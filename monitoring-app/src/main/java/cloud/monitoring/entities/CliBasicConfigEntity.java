package cloud.monitoring.entities;

import javax.persistence.*;
import java.math.BigInteger;
import java.util.List;

/**
 * Created by Roman on 26.10.2017 17:12.
 */
@Entity
@Table(name = "CLI_BASIC_CONFIG")
public class CliBasicConfigEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private BigInteger id;
    @Column(name = "login", nullable = false)
    private String login;
    @Column(name = "password", nullable = false)
    private String password;
    @Column(name = "timeout", nullable = false)
    private Integer timeout;
    @Column(name = "port", nullable = false)
    private Integer port;

    @Column(name = "cron", nullable = false)
    private String cron;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "basic_config", nullable = false, columnDefinition = "integer")
    private BasicConfigEntity basicConfigEntity;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "cliBasicConfigEntity")
    private List<CliMetricConfigEntity> cliMetricConfigEntities;

    public CliBasicConfigEntity() {
    }

    public CliBasicConfigEntity(String login, String password, Integer timeout, Integer port, String cron, BasicConfigEntity basicConfigEntity, List<CliMetricConfigEntity> cliMetricConfigEntities) {
        this.login = login;
        this.password = password;
        this.timeout = timeout;
        this.port = port;
        this.cron = cron;
        this.basicConfigEntity = basicConfigEntity;
        this.cliMetricConfigEntities = cliMetricConfigEntities;
    }

    public BigInteger getId() {
        return id;
    }

    public void setId(BigInteger id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getTimeout() {
        return timeout;
    }

    public void setTimeout(Integer timeout) {
        this.timeout = timeout;
    }

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    public BasicConfigEntity getBasicConfigEntity() {
        return basicConfigEntity;
    }

    public void setBasicConfigEntity(BasicConfigEntity basicConfigEntity) {
        this.basicConfigEntity = basicConfigEntity;
    }

    public String getCron() {
        return cron;
    }

    public void setCron(String cron) {
        this.cron = cron;
    }

    public List<CliMetricConfigEntity> getCliMetricConfigEntities() {
        return cliMetricConfigEntities;
    }

    public void setCliMetricConfigEntities(List<CliMetricConfigEntity> cliMetricConfigEntities) {
        this.cliMetricConfigEntities = cliMetricConfigEntities;
    }

    @Override
    public String toString() {
        return "CliBasicConfigEntity{" +
                "id=" + id +
                ", login='" + login + '\'' +
                ", password='" + password + '\'' +
                ", timeout=" + timeout +
                ", port=" + port +
                ", cron='" + cron + '\'' +
                ", cliMetricConfigEntities=" + cliMetricConfigEntities +
                '}';
    }
}
