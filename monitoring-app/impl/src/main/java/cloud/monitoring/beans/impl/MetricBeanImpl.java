package cloud.monitoring.beans.impl;

import cloud.monitoring.api.entities.rest.MetricResponse;
import cloud.monitoring.api.entities.rest.MetricValue;
import cloud.monitoring.beans.MetricBean;
import cloud.monitoring.entities.Metric;
import cloud.monitoring.repositories.MetricRepository;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Roman on 24.09.2017 11:49.
 */
@Component
public class MetricBeanImpl implements MetricBean {
    private static final Logger LOGGER = Logger.getLogger(MetricBeanImpl.class);
    @Autowired
    MetricRepository metricRepository;

    @Override
    @Transactional
    public void storeMetric(Metric metric) {
        metricRepository.save(metric);
    }

    @Override
    public MetricResponse getMetrics(BigInteger objectID, BigInteger metricTypeID, Integer count) {
        List<Metric> metrics = metricRepository.getMetricsByObjectIDAndMetricTypeIDOrderByDate(objectID, metricTypeID, new PageRequest(0, count, Sort.Direction.DESC, "date"));
        LOGGER.debug("Metrics: "+metrics);
        return formMetricResponse(objectID, metricTypeID, metrics);
    }

    @Override
    public MetricResponse getMetrics(BigInteger objectID, BigInteger metricTypeID, Date date) {
        List<Metric> metrics = metricRepository.getMetricsByObjectIDAndMetricTypeIDAndDateAfterOrderByDateAsc(objectID, metricTypeID, date);
        LOGGER.debug("Metrics: "+metrics);
        return formMetricResponse(objectID, metricTypeID, metrics);
    }

    private MetricResponse formMetricResponse(BigInteger objectID, BigInteger metricTypeID, List<Metric> metrics){
        MetricResponse metricResponse = new MetricResponse();
        metricResponse.setObjectID(objectID);
        metricResponse.setMetricID(metricTypeID);
        List<MetricValue> metricValues = new ArrayList<>();
        for (Metric metric: metrics){
            MetricValue metricValue = new MetricValue(metric.getValue(), metric.getDate());
            metricValues.add(metricValue);
        }
        metricResponse.setMetricValues(metricValues);
        return metricResponse;
    }
}
