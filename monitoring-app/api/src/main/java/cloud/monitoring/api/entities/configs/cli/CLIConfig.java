package cloud.monitoring.api.entities.configs.cli;

import cloud.monitoring.api.entities.configs.Config;

import java.math.BigInteger;
import java.util.List;

/**
 * Created by Roman on 09.09.2017 12:31.
 */
public class CLIConfig extends Config {
    private String login;
    private String password;
    private Integer timeout;
    private Integer port;
    private List<CLIMetricConfig> cliMetricConfigs;

    public CLIConfig() {
    }

    public CLIConfig(BigInteger objectId, String ip, String cron, String login, String password, Integer timeout, Integer port, List<CLIMetricConfig> cliMetricConfigs) {
        super(objectId, ip, cron);
        this.login = login;
        this.password = password;
        this.timeout = timeout;
        this.port = port;
        this.cliMetricConfigs = cliMetricConfigs;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getTimeout() {
        return timeout;
    }

    public void setTimeout(Integer timeout) {
        this.timeout = timeout;
    }

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    public List<CLIMetricConfig> getCliMetricConfigs() {
        return cliMetricConfigs;
    }

    public void setCliMetricConfigs(List<CLIMetricConfig> cliMetricConfigs) {
        this.cliMetricConfigs = cliMetricConfigs;
    }

    @Override
    public String toString() {
        return "CLIConfig{" +
                "login='" + login + '\'' +
                ", password='" + password + '\'' +
                ", timeout=" + timeout +
                ", port=" + port +
                ", cliMetricConfigs=" + cliMetricConfigs +
                '}';
    }
}
