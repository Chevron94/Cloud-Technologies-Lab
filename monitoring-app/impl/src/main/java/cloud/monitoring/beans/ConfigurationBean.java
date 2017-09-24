package cloud.monitoring.beans;

import cloud.monitoring.api.entities.configs.Config;

/**
 * Created by Roman on 24.09.2017 15:49.
 */
public interface ConfigurationBean {
    Boolean applyConfiguration(Config config);
    void reloadConfigurations();
}
