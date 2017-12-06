package ru.vsu.monitoringui.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import ru.vsu.monitoringui.component.SnmpScheduled;
import ru.vsu.monitoringui.mapper.ObjectMapper;
import ru.vsu.monitoringui.models.ObjectModel;
import ru.vsu.monitoringui.models.configs.cli.CLIConfig;
import ru.vsu.monitoringui.models.configs.snmp.SnmpConfig;

@Service
public class ConfigurationService {

	private static final String SNMP_URL = "/configurations/snmp";
	
	private static final String CLI_URL = "/configurations/cli";

	@Autowired
	private ObjectMapper objectMapper;
	
	@Autowired
	private RestTemplate restTemplate;
	
	@Autowired
	private SnmpScheduled scheduled;
	
	public List<CLIConfig> getCliConfigs() {
		List<CLIConfig>  result = new ArrayList<CLIConfig>();
		
		for (ObjectModel model : objectMapper.objects()) {
			CLIConfig conf = sendCliGetRequest(model);
			
			if (conf != null) {
				result.add(conf);
			}
		}
		
		return result;
	}
	
	public List<SnmpConfig> getSnmpConfigs() {
		List<SnmpConfig>  result = new ArrayList<SnmpConfig>();
		
		for (ObjectModel model : objectMapper.objects()) {
			SnmpConfig conf = sendSnmpGetRequest(model);
			
			if (conf != null) {
				result.add(conf);
			}
		}
		
		return result;
	}
	
	public void save(SnmpConfig config) {
		ObjectModel object = objectMapper.object(config.getObjectID());
		SnmpConfig snmpConfig = sendPostRequest(object, SNMP_URL, SnmpConfig.class, config);
		if (snmpConfig != null) {
			scheduled.createSnmpWebClient(object, snmpConfig);
		}
	}
	
	public void save(CLIConfig config) {
		ObjectModel object = objectMapper.object(config.getObjectID());
		sendPostRequest(object, CLI_URL, CLIConfig.class, config);
	}

	protected CLIConfig sendCliGetRequest(ObjectModel object) {
		return sendGetRequest(object, CLI_URL, CLIConfig.class);
	}
	
	protected SnmpConfig sendSnmpGetRequest(ObjectModel object) {
		return sendGetRequest(object, SNMP_URL, SnmpConfig.class);
	}
	
	protected <T> T sendPostRequest(ObjectModel object, String url, Class<T> clazz, T body) {
		UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(object.getUri() + url);
		
		HttpHeaders headers = new HttpHeaders();
		headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);
		
		HttpEntity<T> entity = new HttpEntity<T>(body, headers);
	
		try {
			return restTemplate.exchange(
		        	builder.build().encode().toUri(), 
		        	HttpMethod.POST, 
		        	entity, 
		        	clazz
		        )
				.getBody();
		} catch(Exception e) {
			System.err.println(e.getMessage());
		}
		
		return null;
	}
	
	protected <T> T sendGetRequest(ObjectModel object, String url, Class<T> clazz) {
		UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(object.getUri() + url)
				.queryParam("object-id", object.getObjectId());
		
		HttpHeaders headers = new HttpHeaders();
		headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);
		
		HttpEntity<T> entity = new HttpEntity<T>(headers);
		
		try {
			return restTemplate.exchange(
		        	builder.build().encode().toUri(), 
		        	HttpMethod.GET, 
		        	entity, 
		        	clazz
		        )
				.getBody();
		} catch(Exception e) {
			System.err.println(e.getMessage());
		}
		
		return null;
	}
}
