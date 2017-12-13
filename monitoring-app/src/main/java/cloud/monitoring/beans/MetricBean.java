package cloud.monitoring.beans;

import cloud.monitoring.api.entities.rest.MetricResponse;
import cloud.monitoring.entities.Metric;

import java.math.BigInteger;
import java.util.Date;

/**
 * Created by Roman on 24.09.2017 11:44.
 */
public interface MetricBean {
    void storeMetric(Metric metric);
    MetricResponse getMetrics(BigInteger objectID, BigInteger metricTypeID, Integer count);
    MetricResponse getMetrics(BigInteger objectID, BigInteger metricTypeID, Date date);
}
