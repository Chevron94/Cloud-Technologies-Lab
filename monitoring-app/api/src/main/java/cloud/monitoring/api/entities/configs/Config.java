package cloud.monitoring.api.entities.configs;

import java.math.BigInteger;

/**
 * Created by Roman on 09.09.2017 15:00.
 */
public abstract class Config {
    private BigInteger objectID;
    private String cron;

    public Config() {
    }

    public Config(BigInteger objectID, String cron) {
        this.objectID = objectID;
        this.cron = cron;
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

    @Override
    public String toString() {
        return "Config{" +
                "objectID=" + objectID +
                ", cron='" + cron + '\'' +
                '}';
    }
}
