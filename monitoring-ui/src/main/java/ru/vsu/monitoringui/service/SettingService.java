package ru.vsu.monitoringui.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ru.vsu.monitoringui.mapper.SettingMapper;
import ru.vsu.monitoringui.models.DeviceModel;
import ru.vsu.monitoringui.models.ServerModel;

@Service
public class SettingService {

	@Autowired
	private SettingMapper settingMapper;
	
	public List<ServerModel> getServices() {
		return settingMapper.getServices();
	}
	
	public List<ServerModel> getSelectedServices() {
		return settingMapper.getSelectedServices();
	}
	
	public List<DeviceModel> getSelectedDevices() {
		return settingMapper.getSelectedDevices();
	}
	
	public ServerModel saveServer(ServerModel server) {
		if (server.getId() == null) {
			settingMapper.insertServer(server);
		} else {
			settingMapper.updateServer(server);
		}
		return server;
	}
	
	public void deleteServer(Long id) {
		settingMapper.deleteServer(id);
	}
	
	public List<DeviceModel> getDevices() {
		return settingMapper.getDevices();
	}
	
	public DeviceModel saveDevice(DeviceModel device) {
		if (device.getId() == null) {
			settingMapper.insertDevice(device);
		} else {
			settingMapper.updateDevice(device);
		}
		return device;
	}
	
	public void deleteDevice(Long id) {
		settingMapper.deleteDevice(id);
	}
}
