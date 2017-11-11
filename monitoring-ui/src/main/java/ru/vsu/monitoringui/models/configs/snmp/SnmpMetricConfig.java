package ru.vsu.monitoringui.models.configs.snmp;

import ru.vsu.monitoringui.models.configs.MetricConfig;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigInteger;

/**
 * Created by Roman on 14.09.2017 15:57.
 */
public class SnmpMetricConfig extends MetricConfig {
    @JsonProperty(value = "oid")
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
