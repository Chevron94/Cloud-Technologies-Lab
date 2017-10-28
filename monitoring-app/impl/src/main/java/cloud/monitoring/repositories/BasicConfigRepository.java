package cloud.monitoring.repositories;

import cloud.monitoring.entities.BasicConfigEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.math.BigInteger;

/**
 * Created by Roman on 24.09.2017 12:22.
 */
public interface BasicConfigRepository extends JpaRepository<BasicConfigEntity, BigInteger> {
    BasicConfigEntity getBasicConfigsByObjectID(BigInteger objectID);
}
