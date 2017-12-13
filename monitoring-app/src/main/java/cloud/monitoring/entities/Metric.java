package cloud.monitoring.entities;

import javax.persistence.*;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;

/**
 * Created by Roman on 14.09.2017 16:12.
 */
@Entity
@Table(name = "METRICS")
public class Metric {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private BigInteger id;
    @Column(name = "metric_type_id", nullable = false)
    private BigInteger metricTypeID;
    @Column(name = "object_id", nullable = false)
    private BigInteger objectID;
    @Column(name = "value", nullable = false)
    private BigDecimal value;
    @Column(name = "date", nullable = false)
    private Date date;

    public Metric() {
    }

    public Metric(BigInteger id, BigInteger metricTypeID, BigInteger objectID, BigDecimal value, Date date) {
        this.id = id;
        this.metricTypeID = metricTypeID;
        this.objectID = objectID;
        this.value = value;
        this.date = date;
    }

    public BigInteger getId() {
        return id;
    }

    public void setId(BigInteger id) {
        this.id = id;
    }

    public BigInteger getMetricTypeID() {
        return metricTypeID;
    }

    public void setMetricTypeID(BigInteger metricTypeID) {
        this.metricTypeID = metricTypeID;
    }

    public BigInteger getObjectID() {
        return objectID;
    }

    public void setObjectID(BigInteger objectID) {
        this.objectID = objectID;
    }

    public BigDecimal getValue() {
        return value;
    }

    public void setValue(BigDecimal value) {
        this.value = value;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "Metric{" +
                "id=" + id +
                ", metricTypeID=" + metricTypeID +
                ", objectID=" + objectID +
                ", value=" + value +
                ", date=" + date +
                '}';
    }
}
