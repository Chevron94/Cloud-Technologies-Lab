package cloud.monitoring.repositories;

import cloud.monitoring.entities.SnmpMetricConfigEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.math.BigInteger;
import java.util.List;

/**
 * Created by Roman on 15.10.2017 17:56.
 */
public interface SnmpMetricConfigRepository extends JpaRepository<SnmpMetricConfigEntity,BigInteger>{
    List<SnmpMetricConfigEntity> getSnmpMetricMappingConfigsBySnmpBasicConfigEntity_IdAndOid(BigInteger snmpBasicConfigID, String oid);
    List<SnmpMetricConfigEntity> getSnmpMetricMappingConfigsBySnmpBasicConfigEntity_Id(BigInteger snmpBasicConfigID);
}
