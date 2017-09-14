package cloud.monitoring.impl.repositories;

import cloud.monitoring.impl.entities.Metric;
import org.springframework.data.jpa.repository.JpaRepository;

import java.math.BigInteger;
import java.util.List;

/**
 * Created by Roman on 14.09.2017 16:25.
 */
public interface MetricRepository extends JpaRepository<Metric, BigInteger> {
    List<Metric> getMetricsByObjectID(Long objectID);
    List<Metric> getMetricsByObjectIDAndMetricTypeID(Long objectID, Long metricTypeID);
}
