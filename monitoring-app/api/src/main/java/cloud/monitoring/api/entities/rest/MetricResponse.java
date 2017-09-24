package cloud.monitoring.api.entities.rest;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigInteger;
import java.util.List;

/**
 * Created by Roman on 24.09.2017 11:28.
 */
public class MetricResponse {
    @JsonProperty(value = "object-id")
    private BigInteger objectID;
    @JsonProperty(value = "metric-id")
    private BigInteger metricID;
    @JsonProperty(value = "metric-values")
    private List<MetricValue> metricValues;

    public MetricResponse() {
    }

    public MetricResponse(BigInteger objectID, BigInteger metricID, List<MetricValue> metricValues) {
        this.objectID = objectID;
        this.metricID = metricID;
        this.metricValues = metricValues;
    }

    public BigInteger getObjectID() {
        return objectID;
    }

    public void setObjectID(BigInteger objectID) {
        this.objectID = objectID;
    }

    public BigInteger getMetricID() {
        return metricID;
    }

    public void setMetricID(BigInteger metricID) {
        this.metricID = metricID;
    }

    public List<MetricValue> getMetricValues() {
        return metricValues;
    }

    public void setMetricValues(List<MetricValue> metricValues) {
        this.metricValues = metricValues;
    }

    @Override
    public String toString() {
        return "MetricResponse{" +
                "objectID=" + objectID +
                ", metricID=" + metricID +
                ", metricValues=" + metricValues +
                '}';
    }
}
