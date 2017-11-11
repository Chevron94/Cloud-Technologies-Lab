package ru.vsu.monitoringui.component;

import java.math.BigInteger;
import java.util.Date;

import org.springframework.http.MediaType;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.Message;
import org.springframework.web.reactive.function.client.WebClient;

import reactor.core.publisher.Flux;

public class SnmpWebClient {

	private Integer objectId;
	
	private BigInteger metricId;
	
	private WebClient webClient;
	
	private Long last;
	
	public SnmpWebClient(String uri, Integer objectId, BigInteger metricId) {
		this.objectId = objectId;
		this.metricId = metricId;
		this.webClient = WebClient.create(uri);
		this.last = new Date().getTime();
	}
	
	public Flux<Message<String>> send() {
		final Long start = new Date().getTime(); 
		return webClient.get()
				.uri(builder -> builder.path("/metrics")
					.queryParam("object-id", objectId)
					.queryParam("metric-type-id", metricId)
					.queryParam("from-date", last)
					.build()
				)
				.accept(MediaType.APPLICATION_JSON)
		        .retrieve()
		        .bodyToFlux(String.class)
		        	.map(message -> MessageBuilder.withPayload(message).build())
		        	.doOnComplete(() -> { last = start; });
	}
}
