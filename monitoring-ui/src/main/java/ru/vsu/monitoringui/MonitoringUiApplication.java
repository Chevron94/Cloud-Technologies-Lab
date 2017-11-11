package ru.vsu.monitoringui;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.integration.dsl.channel.MessageChannels;
import org.springframework.messaging.SubscribableChannel;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class MonitoringUiApplication {

	public static void main(String[] args) {
		SpringApplication.run(MonitoringUiApplication.class, args);
	}

	@Bean
	public SubscribableChannel snmpChannel() {
		return MessageChannels.publishSubscribe().get();
	}
}
