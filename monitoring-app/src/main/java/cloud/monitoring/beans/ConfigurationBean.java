package cloud.monitoring.beans;

import cloud.monitoring.api.entities.configs.Config;
import cloud.monitoring.api.entities.configs.cli.CLIConfig;
import cloud.monitoring.api.entities.configs.snmp.SnmpConfig;

import java.math.BigInteger;
import java.util.List;

/**
 * Created by Roman on 24.09.2017 15:49.
 */
public interface ConfigurationBean {
    Boolean applyConfiguration(Config config);
    void reloadConfigurations();
    SnmpConfig getSnmpConfigs(BigInteger objectID);
    CLIConfig getCliConfigs(BigInteger objectID);
}
