package cloud.monitoring.jobs.cli;

import cloud.monitoring.api.entities.configs.cli.CLIConfig;
import cloud.monitoring.api.entities.configs.cli.CLIMetricConfig;
import cloud.monitoring.beans.MetricBean;
import cloud.monitoring.entities.Metric;
import com.jcraft.jsch.*;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Properties;
import java.util.concurrent.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Roman on 09.09.2017 15:15.
 */
public class CLIJob implements Job {
    private static final Logger LOGGER = LoggerFactory.getLogger(CLIJob.class);
    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        JSch jSch = new JSch();
        JobDataMap jobDataMap = jobExecutionContext.getJobDetail().getJobDataMap();
        MetricBean metricBean = (MetricBean) jobDataMap.get("metricBean");
        CLIConfig config = (CLIConfig) jobDataMap.get("config");
        try {
            Session session = jSch.getSession(config.getLogin(), config.getIp(), config.getPort());
            session.setPassword(config.getPassword());
            Properties properties = new Properties();
            properties.put("StrictHostKeyChecking", "no");
            session.setConfig(properties);
            session.connect(config.getTimeout());
            for (CLIMetricConfig metricConfig: config.getCliMetricConfigs()){
                Channel channel = session.openChannel("exec");
                ((ChannelExec) channel).setCommand(metricConfig.getCommand());
                channel.connect();
                final CountDownLatch latch = new CountDownLatch(1);
                StringBuffer output = new StringBuffer();
                CliOutputReader readStdIn = new CliOutputReader(channel.getInputStream(), output, latch, null);
                ExecutorService readerExec = Executors.newCachedThreadPool();
                Future<?> future = readerExec.submit(readStdIn);
                try {
                    boolean finishedNormally = latch.await(config.getTimeout(), TimeUnit.MILLISECONDS);
                    if (!finishedNormally) {
                        LOGGER.warn("Reading output timeout after waiting for {} seconds", TimeUnit.MILLISECONDS.toSeconds(config.getTimeout()));
                    }
                } catch (InterruptedException e) {
                    LOGGER.warn("e", e);
                    future.cancel(true);
                }

                String commandResult = output.toString().trim();
                Matcher matcher = Pattern.compile(metricConfig.getRegexp()).matcher(commandResult);
                if (!matcher.find()) {
                    LOGGER.error("Cannot find matched value in command output, pattern: {}, , output: {}", metricConfig.getRegexp(), commandResult);
                    continue;
                }
                commandResult =  matcher.group();
                LOGGER.debug("] Command output: {}", commandResult);
                Metric metric = new Metric();
                metric.setObjectID(config.getObjectID());
                metric.setDate(new Date());
                metric.setValue(new BigDecimal(commandResult));
                metric.setMetricTypeID(metricConfig.getMetricID());
                LOGGER.debug("Collected metric: {}", metric);
                metricBean.storeMetric(metric);
            }


        } catch (JSchException ex) {
            LOGGER.error("Unable to get session, ex:", ex);
        } catch (IOException ex) {
            LOGGER.error("Unable to get output, ex: ", ex);
        }
    }
}
