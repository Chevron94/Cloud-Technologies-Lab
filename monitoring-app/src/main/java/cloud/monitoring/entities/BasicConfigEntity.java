package cloud.monitoring.entities;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.math.BigInteger;
import java.util.List;

/**
 * Created by Roman on 15.10.2017 17:34.
 */
@Entity
@Table(name = "BASIC_CONFIG")
public class BasicConfigEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private BigInteger id;
    @Column(name = "object_id", nullable = false)
    private BigInteger objectID;
    @Column(name = "ip", nullable = false)
    private String ip;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "basicConfigEntity")
    @Fetch(FetchMode.SELECT)
    private List<SnmpBasicConfigEntity> snmpBaseConfigs;
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "basicConfigEntity")
    @Fetch(FetchMode.SELECT)
    private List<CliBasicConfigEntity> cliBasicConfigEntities;

    public BasicConfigEntity() {
    }

    public BasicConfigEntity(BigInteger objectID, String ip, List<SnmpBasicConfigEntity> snmpBaseConfigs, List<CliBasicConfigEntity> cliBasicConfigEntities) {
        this.objectID = objectID;
        this.ip = ip;
        this.snmpBaseConfigs = snmpBaseConfigs;
        this.cliBasicConfigEntities = cliBasicConfigEntities;
    }

    public BigInteger getId() {
        return id;
    }

    public void setId(BigInteger id) {
        this.id = id;
    }

    public BigInteger getObjectID() {
        return objectID;
    }

    public void setObjectID(BigInteger objectID) {
        this.objectID = objectID;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public List<SnmpBasicConfigEntity> getSnmpBaseConfigs() {
        return snmpBaseConfigs;
    }

    public void setSnmpBaseConfigs(List<SnmpBasicConfigEntity> snmpBaseConfigs) {
        this.snmpBaseConfigs = snmpBaseConfigs;
    }

    public List<CliBasicConfigEntity> getCliBasicConfigEntities() {
        return cliBasicConfigEntities;
    }

    public void setCliBasicConfigEntities(List<CliBasicConfigEntity> cliBasicConfigEntities) {
        this.cliBasicConfigEntities = cliBasicConfigEntities;
    }

    @Override
    public String toString() {
        return "BasicConfigEntity{" +
                "id=" + id +
                ", objectID=" + objectID +
                ", ip='" + ip + '\'' +
                '}';
    }
}
