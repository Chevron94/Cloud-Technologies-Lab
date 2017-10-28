package cloud.monitoring.entities;

import javax.persistence.*;
import java.math.BigInteger;

/**
 * Created by Roman on 26.10.2017 17:15.
 */
@Entity
@Table(name = "CLI_METRIC_MAPPING_CONFIG")
public class CliMetricConfigEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private BigInteger id;
    @Column(name = "metric_type_id", nullable = false)
    private BigInteger metricTypeID;
    @Column(name = "command", nullable = false)
    private String command;
    @Column(name = "regexp", nullable = false)
    private String regexp;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cli_basic_config", nullable = false, columnDefinition = "integer")
    private CliBasicConfigEntity cliBasicConfigEntity;

    public CliMetricConfigEntity() {
    }

    public CliMetricConfigEntity(BigInteger metricTypeID, String command, String regexp, CliBasicConfigEntity cliBasicConfigEntity) {
        this.metricTypeID = metricTypeID;
        this.command = command;
        this.regexp = regexp;
        this.cliBasicConfigEntity = cliBasicConfigEntity;
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

    public String getCommand() {
        return command;
    }

    public void setCommand(String command) {
        this.command = command;
    }

    public String getRegexp() {
        return regexp;
    }

    public void setRegexp(String regexp) {
        this.regexp = regexp;
    }

    public CliBasicConfigEntity getCliBasicConfigEntity() {
        return cliBasicConfigEntity;
    }

    public void setCliBasicConfigEntity(CliBasicConfigEntity cliBasicConfigEntity) {
        this.cliBasicConfigEntity = cliBasicConfigEntity;
    }

    @Override
    public String toString() {
        return "CliMetricConfigEntity{" +
                "id=" + id +
                ", metricTypeID=" + metricTypeID +
                ", command='" + command + '\'' +
                ", regexp='" + regexp + '\'' +
                '}';
    }
}
