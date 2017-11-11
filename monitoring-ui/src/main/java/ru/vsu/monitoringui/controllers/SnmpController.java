package ru.vsu.monitoringui.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.messaging.MessageHandler;
import org.springframework.messaging.SubscribableChannel;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import reactor.core.publisher.Flux;

@RestController
public class SnmpController {
		
	@Autowired
	private SubscribableChannel snmpChannel;
	
		
	@GetMapping(value = "/metrics/{object}", produces = MediaType.TEXT_EVENT_STREAM_VALUE )
	public Flux<String> metrics(@PathVariable String object) {
		return Flux.create(sink -> {
			MessageHandler messageHandler = msg -> sink.next(String.class.cast(msg.getPayload()));			snmpChannel.subscribe(messageHandler);
		});
	}
		
}