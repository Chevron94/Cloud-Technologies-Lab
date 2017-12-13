package cloud.monitoring.api.entities.configs.cli;

import cloud.monitoring.api.entities.configs.MetricConfig;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigInteger;

/**
 * Created by Roman on 09.09.2017 12:31.
 */
public class CLIMetricConfig extends MetricConfig {
    @JsonProperty(value = "command")
    private String command;
    @JsonProperty(value = "regexp")
    private String regexp;

    public CLIMetricConfig() {
    }

    public CLIMetricConfig(String command, String regexp, BigInteger metricID) {
        super(metricID);
        this.command = command;
        this.regexp = regexp;
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

    @Override
    public String toString() {
        return "CLIMetricConfig{" +
                "command='" + command + '\'' +
                ", regexp='" + regexp + '\'' +
                '}';
    }
}
