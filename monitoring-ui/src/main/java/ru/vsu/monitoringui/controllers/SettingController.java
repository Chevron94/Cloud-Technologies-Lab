package ru.vsu.monitoringui.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import ru.vsu.monitoringui.models.DeviceModel;
import ru.vsu.monitoringui.models.JsonResult;
import ru.vsu.monitoringui.models.ServerModel;
import ru.vsu.monitoringui.service.SettingService;

@RestController
public class SettingController {

	@Autowired
	private SettingService service;
	
	@RequestMapping(value="/api/settings/services/list", method = RequestMethod.GET)
	public ResponseEntity<JsonResult> getSelectedServices() {
		JsonResult result = new JsonResult();
		result.setResult(service.getSelectedServices());
		return new ResponseEntity<JsonResult>(result, HttpStatus.OK);
	}
	
	@RequestMapping(value="/api/settings/devices/list", method = RequestMethod.GET)
	public ResponseEntity<JsonResult> getSelectedDevices() {
		JsonResult result = new JsonResult();
		result.setResult(service.getSelectedDevices());
		return new ResponseEntity<JsonResult>(result, HttpStatus.OK);
	}
	
	@RequestMapping(value="/api/settings/services", method = RequestMethod.GET)
	public ResponseEntity<JsonResult> getServicies() {
		JsonResult result = new JsonResult();
		result.setResult(service.getServices());
		return new ResponseEntity<JsonResult>(result, HttpStatus.OK);
	}
	
	@RequestMapping(value="/api/settings/services", method = RequestMethod.POST)
	public ResponseEntity<JsonResult> saveServer(@RequestBody ServerModel serverModel) {
		JsonResult result = new JsonResult();
		result.setResult(service.saveServer(serverModel));
		return new ResponseEntity<JsonResult>(result, HttpStatus.OK);
	}
	
	@RequestMapping(value="/api/settings/services/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<JsonResult> deleteServer(@PathVariable("id") Long id) {
		service.deleteServer(id);
		return new ResponseEntity<JsonResult>(HttpStatus.OK);
	}
	
	@RequestMapping(value="/api/settings/devices", method = RequestMethod.GET)
	public ResponseEntity<JsonResult> getDevices() {
		JsonResult result = new JsonResult();
		result.setResult(service.getDevices());
		return new ResponseEntity<JsonResult>(result, HttpStatus.OK);
	}
	
	@RequestMapping(value="/api/settings/devices", method = RequestMethod.POST)
	public ResponseEntity<JsonResult> saveDevice(@RequestBody DeviceModel device) {
		JsonResult result = new JsonResult();
		result.setResult(service.saveDevice(device));
		return new ResponseEntity<JsonResult>(result, HttpStatus.OK);
	}
	
	@RequestMapping(value="/api/settings/devices/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<JsonResult> deleteDevice(@PathVariable("id") Long id) {
		service.deleteDevice(id);
		return new ResponseEntity<JsonResult>(HttpStatus.OK);
	}
}
