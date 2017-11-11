package ru.vsu.monitoringui.models.configs;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigInteger;

/**
 * Created by Roman on 14.09.2017 15:55.
 */
public class MetricConfig {
    @JsonProperty(value = "metric-id")
    private BigInteger metricID;

    public MetricConfig() {
    }

    public MetricConfig(BigInteger metricID) {
        this.metricID = metricID;
    }

    public BigInteger getMetricID() {
        return metricID;
    }

    public void setMetricID(BigInteger metricID) {
        this.metricID = metricID;
    }

    @Override
    public String toString() {
        return "MetricConfig{" +
                "metricID=" + metricID +
                '}';
    }
}
