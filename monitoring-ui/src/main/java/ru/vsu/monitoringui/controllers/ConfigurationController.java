package ru.vsu.monitoringui.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import ru.vsu.monitoringui.models.JsonResult;
import ru.vsu.monitoringui.models.configs.cli.CLIConfig;
import ru.vsu.monitoringui.models.configs.snmp.SnmpConfig;
import ru.vsu.monitoringui.service.ConfigurationService;

@RestController
public class ConfigurationController {

	@Autowired
	private ConfigurationService configurationService;
	
	@RequestMapping(value="/api/configuration/cli", method = RequestMethod.POST)
	public ResponseEntity<JsonResult> saveCli(@RequestBody CLIConfig config) {
		configurationService.save(config);
		return new ResponseEntity<JsonResult>(HttpStatus.OK);
	}
	
	@RequestMapping(value="/api/configuration/snmp", method = RequestMethod.POST)
	public ResponseEntity<JsonResult> saveSnmp(@RequestBody SnmpConfig config) {
		configurationService.save(config);
		return new ResponseEntity<JsonResult>(HttpStatus.OK);
	}
	
	@RequestMapping(value="/api/configuration/cli", method = RequestMethod.GET)
	public ResponseEntity<JsonResult> getClis() {
		JsonResult result = new JsonResult();
		result.setResult(configurationService.getCliConfigs());
		return new ResponseEntity<JsonResult>(result, HttpStatus.OK);
	}
	
	@RequestMapping(value="/api/configuration/snmp", method = RequestMethod.GET)
	public ResponseEntity<JsonResult> getSnmps() {
		JsonResult result = new JsonResult();
		result.setResult(configurationService.getSnmpConfigs());
		return new ResponseEntity<JsonResult>(result, HttpStatus.OK);
	}
}
