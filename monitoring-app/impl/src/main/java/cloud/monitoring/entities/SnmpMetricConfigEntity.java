package cloud.monitoring.entities;

import javax.persistence.*;
import java.math.BigInteger;

/**
 * Created by Roman on 15.10.2017 17:38.
 */
@Entity
@Table(name = "SNMP_METRIC_MAPPING_CONFIG")
public class SnmpMetricConfigEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private BigInteger id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "snmp_basic_config", nullable = false, columnDefinition = "integer")
    private SnmpBasicConfigEntity snmpBasicConfigEntity;
    @Column(name = "metric_type_id", nullable = false)
    private BigInteger metricTypeID;
    @Column(name = "oid", nullable = false)
    private String oid;

    public SnmpMetricConfigEntity() {
    }

    public SnmpMetricConfigEntity(SnmpBasicConfigEntity snmpBasicConfigEntity, BigInteger metricTypeID, String oid) {
        this.snmpBasicConfigEntity = snmpBasicConfigEntity;
        this.metricTypeID = metricTypeID;
        this.oid = oid;
    }

    public BigInteger getId() {
        return id;
    }

    public void setId(BigInteger id) {
        this.id = id;
    }

    public SnmpBasicConfigEntity getSnmpBasicConfigEntity() {
        return snmpBasicConfigEntity;
    }

    public void setSnmpBasicConfigEntity(SnmpBasicConfigEntity snmpBasicConfigEntity) {
        this.snmpBasicConfigEntity = snmpBasicConfigEntity;
    }

    public BigInteger getMetricTypeID() {
        return metricTypeID;
    }

    public void setMetricTypeID(BigInteger metricTypeID) {
        this.metricTypeID = metricTypeID;
    }

    public String getOid() {
        return oid;
    }

    public void setOid(String oid) {
        this.oid = oid;
    }

    @Override
    public String toString() {
        return "SnmpMetricConfigEntity{" +
                "id=" + id +
                ", snmpBasicConfigEntity=" + snmpBasicConfigEntity +
                ", metricTypeID=" + metricTypeID +
                ", oid='" + oid + '\'' +
                '}';
    }
}
