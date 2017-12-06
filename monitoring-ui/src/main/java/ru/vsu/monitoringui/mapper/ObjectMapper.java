package ru.vsu.monitoringui.mapper;

import java.math.BigInteger;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import ru.vsu.monitoringui.models.ObjectModel;

@Mapper
public interface ObjectMapper {

	@Select(  "	select s.uri as uri, o.id as objectId "
			+ "	from server as s join object as o on s.id = o.server_id")
	public List<ObjectModel> objects();
	
	@Select(  "	select s.uri as uri, o.id as objectId "
			+ "	from server as s join object as o on s.id = o.server_id"
			+ " where o.id = #{id} ")
	public ObjectModel object(@Param("id") BigInteger id);
	
}
