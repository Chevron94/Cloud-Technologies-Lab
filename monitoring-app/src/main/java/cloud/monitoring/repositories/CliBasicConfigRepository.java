package cloud.monitoring.repositories;

import cloud.monitoring.entities.CliBasicConfigEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.math.BigInteger;
import java.util.List;

/**
 * Created by Roman on 26.10.2017 17:19.
 */
public interface CliBasicConfigRepository extends JpaRepository<CliBasicConfigEntity, BigInteger> {
    List<CliBasicConfigEntity> getCliBasicConfigsByBasicConfigEntity_Id(BigInteger basicConfigID);
}
