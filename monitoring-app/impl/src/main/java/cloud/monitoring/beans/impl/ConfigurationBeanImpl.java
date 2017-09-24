package cloud.monitoring.beans.impl;

import cloud.monitoring.api.entities.configs.Config;
import cloud.monitoring.beans.ConfigurationBean;
import cloud.monitoring.jobs.JobPool;
//import cloud.monitoring.repositories.ConfigRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by Roman on 24.09.2017 15:51.
 */
@Component
public class ConfigurationBeanImpl implements ConfigurationBean {
//    @Autowired
//    ConfigRepository configRepository;
    @Autowired
    JobPool jobPool;

    @Override
    public Boolean applyConfiguration(Config config) {
        try {
            jobPool.createJob(config);
            return true;
        } catch (IllegalStateException ex) {
            return false;
        }
    }

    @Override
    public void reloadConfigurations() {

    }
}
