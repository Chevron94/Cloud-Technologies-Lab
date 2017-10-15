package cloud.monitoring.repositories;

import cloud.monitoring.entities.SnmpBasicConfigEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.math.BigInteger;
import java.util.List;

/**
 * Created by Roman on 15.10.2017 17:55.
 */
public interface SnmpBasicConfigRepository extends JpaRepository<SnmpBasicConfigEntity, BigInteger> {
    List<SnmpBasicConfigEntity> getSnmpBasicConfigsByBasicConfigEntity_Id(BigInteger basicConfigID);
}
