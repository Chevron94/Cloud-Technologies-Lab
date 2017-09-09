package cloud.monitoring;

import org.quartz.SchedulerException;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.io.IOException;

@SpringBootApplication
@EnableScheduling
public class MonitoringAppApplication {

	public static void main(String[] args) throws IOException, SchedulerException {
		SpringApplication.run(MonitoringAppApplication.class, args);
	}
}
