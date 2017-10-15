package cloud.monitoring.entities;

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
    @Column(name = "cron", nullable = false)
    private String cron;
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "basicConfigEntity")
    private List<SnmpBasicConfigEntity> snmpBaseConfigs;

    public BasicConfigEntity() {
    }

    public BasicConfigEntity(BigInteger objectID, String ip, String cron, List<SnmpBasicConfigEntity> snmpBaseConfigs) {
        this.objectID = objectID;
        this.ip = ip;
        this.cron = cron;
        this.snmpBaseConfigs = snmpBaseConfigs;
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

    public String getCron() {
        return cron;
    }

    public void setCron(String cron) {
        this.cron = cron;
    }

    public List<SnmpBasicConfigEntity> getSnmpBaseConfigs() {
        return snmpBaseConfigs;
    }

    public void setSnmpBaseConfigs(List<SnmpBasicConfigEntity> snmpBaseConfigs) {
        this.snmpBaseConfigs = snmpBaseConfigs;
    }

    @Override
    public String toString() {
        return "BasicConfigEntity{" +
                "id=" + id +
                ", objectID=" + objectID +
                ", ip='" + ip + '\'' +
                ", cron='" + cron + '\'' +
                '}';
    }
}
