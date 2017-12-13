package cloud.monitoring.api.entities.rest;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by Roman on 24.09.2017 11:32.
 */
public class MetricValue {
    @JsonProperty(value = "value")
    private BigDecimal value;
    @JsonProperty(value = "date")
    private Date date;

    public MetricValue() {
    }

    public MetricValue(BigDecimal value, Date date) {
        this.value = value;
        this.date = date;
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
        return "MetricValue{" +
                "value=" + value +
                ", date=" + date +
                '}';
    }
}
