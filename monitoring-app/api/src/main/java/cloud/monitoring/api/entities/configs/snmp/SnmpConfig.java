package cloud.monitoring.api.entities.configs.snmp;

import cloud.monitoring.api.entities.configs.Config;

import java.math.BigInteger;
import java.util.List;

/**
 * Created by Roman on 09.09.2017 12:29.
 */
public class SnmpConfig extends Config {
    private String community;
    private String ip;
    private BigInteger version;
    private Integer port;
    private Integer timeout;
    private List<String> metrics;

    public SnmpConfig() {
    }

    public SnmpConfig(BigInteger objectID, String cron,  String community, String ip, BigInteger version, Integer port, Integer timeout, List<String> metrics) {
        super(objectID, cron);
        this.community = community;
        this.ip = ip;
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

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public BigInteger getVersion() {
        return version;
    }

    public void setVersion(BigInteger version) {
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

    public List<String> getMetrics() {
        return metrics;
    }

    public void setMetrics(List<String> metrics) {
        this.metrics = metrics;
    }

    @Override
    public String toString() {
        return "SnmpConfig{" +
                "community='" + community + '\'' +
                ", ip='" + ip + '\'' +
                ", version=" + version +
                ", port=" + port +
                ", timeout=" + timeout +
                ", metrics=" + metrics +
                '}';
    }
}
