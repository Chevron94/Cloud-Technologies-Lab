package cloud.monitoring.jobs;

import cloud.monitoring.api.entities.configs.Config;
import cloud.monitoring.api.entities.configs.snmp.SnmpConfig;
import cloud.monitoring.beans.MetricBean;
import cloud.monitoring.jobs.cli.CLIJob;
import cloud.monitoring.jobs.snmp.SnmpJob;
import org.apache.log4j.Logger;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;

import static org.quartz.JobBuilder.newJob;

/**
 * Created by Roman on 09.09.2017 14:25.
 */
@Component
public class JobPool {

    @Autowired
    MetricBean metricBean;
    private static final Logger LOGGER = Logger.getLogger(JobPool.class);
    private static Scheduler scheduler;

    static {
        try {
            scheduler = new StdSchedulerFactory().getScheduler();
            scheduler.start();
        } catch (Exception ex) {
            //
        }
    }

    public void createJob(Config config) {
        JobDataMap jobDataMap = new JobDataMap();
        jobDataMap.put("config", config);
        jobDataMap.put("metricBean", metricBean);
        String key;
        Class jobClass;
        if (config instanceof SnmpConfig) {
            key = "SNMP_" + config.getObjectID();
            jobClass = SnmpJob.class;
        } else {
            key = "CLI_" + config.getObjectID();
            jobClass = CLIJob.class;
        }
        try {
            JobDetail job = newJob(jobClass)
                    .withIdentity(key)
                    .setJobData(jobDataMap).build();
            Trigger trigger = TriggerBuilder.newTrigger()
                    .withIdentity(key)
                    .withSchedule(CronScheduleBuilder.cronSchedule(config.getCron())).startNow().build();

            if (scheduler.checkExists(new TriggerKey(key))) {
                scheduler.rescheduleJob(new TriggerKey(key), trigger);
            }
            if (scheduler.checkExists(new JobKey(key))) {
                scheduler.addJob(job, true);
            } else {
                scheduler.scheduleJob(job, trigger);
            }
        } catch (Exception ex) {
            LOGGER.error("Configuration Apply Failed! ex: ", ex);
            throw new IllegalStateException("Configuration Apply Failed!");
        }
    }
}
