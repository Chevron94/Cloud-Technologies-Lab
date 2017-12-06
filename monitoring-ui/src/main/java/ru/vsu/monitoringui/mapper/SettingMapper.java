package ru.vsu.monitoringui.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectKey;
import org.apache.ibatis.annotations.Update;

import ru.vsu.monitoringui.models.DeviceModel;
import ru.vsu.monitoringui.models.ServerModel;

@Mapper
public interface SettingMapper {

	@Select(  "	select distinct s.name as name, s.id as id "
			+ "	from server as s")
	List<ServerModel> getSelectedServices();
	
	@Select(  "	select distinct d.name as name, d.id as id "
			+ "	from object as d")
	List<DeviceModel> getSelectedDevices();
	
	@Select(  "	select s.uri as uri, s.name as name, s.id as id "
			+ "	from server as s")
	List<ServerModel> getServices();
	
	@Insert(  " insert into server(name, uri) values(#{name}, #{uri}) ")
	@SelectKey(statement="SELECT currval('server_id_seq')", keyProperty="id", before=false, resultType=long.class)
	int insertServer(ServerModel server);
	
	@Update(  " update server set name = #{name}, uri = #{uri} where id = #{id} ")
	int updateServer(ServerModel server);
	
	@Delete( " delete from server where id = #{id}" )
	int deleteServer(@Param("id") Long id);
	
	@Select(  "	select o.server_id as serverId, o.name as name, o.id as id "
			+ "	from object as o ")
	List<DeviceModel> getDevices();
	
	@Insert(  " insert into object(server_id, name) values(#{serverId}, #{name}) ")
	@SelectKey(statement="SELECT currval('object_id_seq')", keyProperty="id", before=false, resultType=long.class)
	int insertDevice(DeviceModel device);
	
	@Update(  " update object set name = #{name}, server_id = #{serverId} where id = #{id} ")
	int updateDevice(DeviceModel device);
	
	@Delete( " delete from object where id = #{id}" )
	int deleteDevice(@Param("id") Long id);
}
