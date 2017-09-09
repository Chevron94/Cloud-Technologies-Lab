package cloud.monitoring.impl.jobs;

import cloud.monitoring.api.entities.configs.Config;
import cloud.monitoring.api.entities.configs.snmp.SnmpConfig;
import cloud.monitoring.impl.jobs.cli.CLIJob;
import cloud.monitoring.impl.jobs.snmp.SnmpJob;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

import static org.quartz.JobBuilder.newJob;

/**
 * Created by Roman on 09.09.2017 14:25.
 */
public class JobPool {

    private static Scheduler scheduler;

    static {
        try {
            scheduler = new StdSchedulerFactory().getScheduler();
            scheduler.start();
        } catch (Exception ex) {
            //
        }

    }

    public static void createOrUpdateSNMPJob(Config config) throws SchedulerException {
        JobDataMap jobDataMap = new JobDataMap();
        jobDataMap.put("config", config);
        String key;
        Class jobClass;
        if (config instanceof SnmpConfig) {
            key = "SNMP_"+config.getObjectID();
            jobClass = SnmpJob.class;
        } else {
            key = "CLI_"+config.getObjectID();
            jobClass = CLIJob.class;
        }
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
    }
}
