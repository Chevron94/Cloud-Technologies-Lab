package cloud.monitoring.api.entities.configs.cli;

/**
 * Created by Roman on 09.09.2017 12:31.
 */
public class CLIMetricConfig {
    private String command;
    private String regexp;

    public CLIMetricConfig() {
    }

    public CLIMetricConfig(String command, String regexp) {
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
