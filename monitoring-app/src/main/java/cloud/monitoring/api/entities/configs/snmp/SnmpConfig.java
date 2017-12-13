package cloud.monitoring.api.entities.configs.snmp;

import cloud.monitoring.api.entities.configs.Config;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigInteger;
import java.util.List;

/**
 * Created by Roman on 09.09.2017 12:29.
 */
public class SnmpConfig extends Config {
    @JsonProperty(value = "community")
    private String community;
    @JsonProperty(value = "version")
    private Integer version;
    @JsonProperty(value = "port")
    private Integer port;
    @JsonProperty(value = "timeout")
    private Integer timeout;
    @JsonProperty(value = "metrics")
    private List<SnmpMetricConfig> metrics;

    public SnmpConfig() {
    }

    public SnmpConfig(BigInteger objectID, String cron,  String community, String ip, Integer version, Integer port, Integer timeout, List<SnmpMetricConfig> metrics) {
        super(objectID, ip, cron);
        this.community = community;
        this.version = version;
        this.port = port;
        this.timeout = timeout;
        this.metrics = metrics;
    }

    public String getCommunity() {
        return community;
    }

    public void setCommunity(String community) {
        this.community = community;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    public Integer getTimeout() {
        return timeout;
    }

    public void setTimeout(Integer timeout) {
        this.timeout = timeout;
    }

    public List<SnmpMetricConfig> getMetrics() {
        return metrics;
    }

    public void setMetrics(List<SnmpMetricConfig> metrics) {
        this.metrics = metrics;
    }

    @Override
    public String toString() {
        return "SnmpConfig{" +
                "community='" + community + '\'' +
                ", version=" + version +
                ", port=" + port +
                ", timeout=" + timeout +
                ", metrics=" + metrics +
                '}';
    }
}
