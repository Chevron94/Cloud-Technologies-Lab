package cloud.monitoring.entities;

import javax.persistence.*;
import java.math.BigInteger;
import java.util.List;

/**
 * Created by Roman on 15.10.2017 17:35.
 */
@Entity
@Table(name = "SNMP_BASIC_CONFIG")
public class SnmpBasicConfigEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private BigInteger id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "basic_config", nullable = false, columnDefinition = "integer")
    private BasicConfigEntity basicConfigEntity;
    @Column(name = "community", nullable = false)
    private String community;
    @Column(name = "version", nullable = false)
    private Integer version;
    @Column(name = "port", nullable = false)
    private Integer port;
    @Column(name = "timeout", nullable = false)
    private Integer timeout;
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "snmpBasicConfigEntity")
    private List<SnmpMetricConfigEntity> snmpMetricConfigEntities;

    public SnmpBasicConfigEntity() {
    }

    public SnmpBasicConfigEntity(BasicConfigEntity basicConfigEntity, String community, Integer version, Integer port, Integer timeout, List<SnmpMetricConfigEntity> snmpMetricConfigEntities) {
        this.basicConfigEntity = basicConfigEntity;
        this.community = community;
        this.version = version;
        this.port = port;
        this.timeout = timeout;
        this.snmpMetricConfigEntities = snmpMetricConfigEntities;
    }

    public BigInteger getId() {
        return id;
    }

    public void setId(BigInteger id) {
        this.id = id;
    }

    public BasicConfigEntity getBasicConfigEntity() {
        return basicConfigEntity;
    }

    public void setBasicConfigEntity(BasicConfigEntity basicConfigEntity) {
        this.basicConfigEntity = basicConfigEntity;
    }

    public String getCommunity() {
        return community;
    }

    public void setCommunity(String community) {
        this.community = community;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    public Integer getTimeout() {
        return timeout;
    }

    public void setTimeout(Integer timeout) {
        this.timeout = timeout;
    }

    public List<SnmpMetricConfigEntity> getSnmpMetricConfigEntities() {
        return snmpMetricConfigEntities;
    }

    public void setSnmpMetricConfigEntities(List<SnmpMetricConfigEntity> snmpMetricConfigEntities) {
        this.snmpMetricConfigEntities = snmpMetricConfigEntities;
    }

    @Override
    public String toString() {
        return "SnmpBasicConfigEntity{" +
                "id=" + id +
                ", basicConfigEntity=" + basicConfigEntity +
                ", community='" + community + '\'' +
                ", version=" + version +
                ", port=" + port +
                ", timeout=" + timeout +
                '}';
    }
}
