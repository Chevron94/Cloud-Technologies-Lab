package cloud.monitoring.api.entities.configs.snmp;

import cloud.monitoring.api.entities.configs.MetricConfig;

import java.math.BigInteger;

/**
 * Created by Roman on 14.09.2017 15:57.
 */
public class SnmpMetricConfig extends MetricConfig {
    private String oid;

    public SnmpMetricConfig() {
    }

    public SnmpMetricConfig(BigInteger metricID, String oid) {
        super(metricID);
        this.oid = oid;
    }

    public String getOid() {
        return oid;
    }

    public void setOid(String oid) {
        this.oid = oid;
    }

    @Override
    public String toString() {
        return "SnmpMetricConfig{" +
                "oid='" + oid + '\'' +
                '}';
    }
}
