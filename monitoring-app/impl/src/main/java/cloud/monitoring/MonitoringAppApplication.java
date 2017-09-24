package cloud.monitoring;

import cloud.monitoring.beans.ConfigurationBean;
import org.quartz.SchedulerException;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;

import java.io.IOException;

@SpringBootApplication
@ComponentScan
public class MonitoringAppApplication {

	public static void main(String[] args) throws IOException, SchedulerException {
        ConfigurableApplicationContext context = SpringApplication.run(MonitoringAppApplication.class, args);
        ConfigurationBean configurationBean = context.getBean(ConfigurationBean.class);
        configurationBean.reloadConfigurations();
	}
}
