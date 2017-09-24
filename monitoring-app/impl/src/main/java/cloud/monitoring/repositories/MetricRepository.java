package cloud.monitoring.repositories;

import cloud.monitoring.entities.Metric;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.math.BigInteger;
import java.util.Date;
import java.util.List;

/**
 * Created by Roman on 14.09.2017 16:25.
 */
public interface MetricRepository extends JpaRepository<Metric, BigInteger> {
    List<Metric> getMetricsByObjectIDAndMetricTypeIDOrderByDate(BigInteger objectID, BigInteger metricTypeID, Pageable pageable);
    List<Metric> getMetricsByObjectIDAndMetricTypeIDAndDateAfterOrderByDateAsc(BigInteger objectID, BigInteger metricTypeID, Date after);
}
