package cloud.monitoring.api.entities.configs;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigInteger;

/**
 * Created by Roman on 09.09.2017 15:00.
 */
public class Config {
    @JsonProperty(value = "object-id")
    private BigInteger objectID;
    @JsonProperty(value = "ip")
    private String ip;
    @JsonProperty(value = "cron")
    private String cron;

    public Config() {
    }

    public Config(BigInteger objectID, String ip, String cron) {
        this.objectID = objectID;
        this.cron = cron;
        this.ip = ip;
    }

    public BigInteger getObjectID() {
        return objectID;
    }

    public void setObjectID(BigInteger objectID) {
        this.objectID = objectID;
    }

    public String getCron() {
        return cron;
    }

    public void setCron(String cron) {
        this.cron = cron;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    @Override
    public String toString() {
        return "Config{" +
                "objectID=" + objectID +
                ", ip='" + ip + '\'' +
                ", cron='" + cron + '\'' +
                '}';
    }
}
