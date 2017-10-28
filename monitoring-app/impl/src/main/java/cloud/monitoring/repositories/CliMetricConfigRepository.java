package cloud.monitoring.repositories;

import cloud.monitoring.entities.CliMetricConfigEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.math.BigInteger;
import java.util.List;

/**
 * Created by Roman on 26.10.2017 17:21.
 */
public interface CliMetricConfigRepository extends JpaRepository<CliMetricConfigEntity, BigInteger> {
    List<CliMetricConfigEntity> getCliMetricMappingConfigsByCliBasicConfigEntity_Id(BigInteger snmpBasicConfigID);
}
