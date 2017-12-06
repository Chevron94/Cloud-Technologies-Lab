package ru.vsu.monitoringui.component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.SubscribableChannel;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import ru.vsu.monitoringui.mapper.ObjectMapper;
import ru.vsu.monitoringui.models.ObjectModel;
import ru.vsu.monitoringui.models.configs.snmp.SnmpConfig;

@Component
public class SnmpScheduled implements InitializingBean {
		
	private List<SnmpWebClient> clients = new ArrayList<SnmpWebClient>();
	
	@Autowired
	private ObjectMapper objectMapper;
	
	@Autowired
	private SubscribableChannel snmpChannel;
	
	@Override
	public void afterPropertiesSet() throws Exception {
		objectMapper.objects().forEach(this::createSnmpWebClients);
	}
	
	@Scheduled(fixedRate = 60 * 1000)
	public void scheduled() {
		clients.forEach(this::sendAndSubscribe);
	}
	
	private void sendAndSubscribe(SnmpWebClient client) {
		client.send().subscribe(message -> snmpChannel.send(message));
	}
	
	private void createSnmpWebClients(ObjectModel object) {
		WebClient.create(object.getUri()).get()
			.uri(builder -> builder.path("/configurations/snmp")
			.queryParam("object-id", object.getObjectId()).build())
	        .retrieve()
	        .bodyToMono(SnmpConfig.class)
	        .subscribe(config -> createSnmpWebClient(object, config));
	}
	
	public void createSnmpWebClient(ObjectModel object, SnmpConfig config) {
		clients.addAll(config.getMetrics()
				.stream()
				.map(m -> new SnmpWebClient(object.getUri(), object.getObjectId(), m.getMetricID()))
				.collect(Collectors.toList()));
	}
}
