package com.ecpbm.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.ecpbm.pojo.Type;

public interface TypeMapper {
	//��ѯ������Ʒ����
	@Select("select * from type")
	public List<Type> selectAll();
	//���ͱ�Ų�����
	@Select("select * from type where id=#{id}")
	public Type selectById(@Param("id") int id);
	//�����Ʒ����
	@Insert("insert into type(name) value(#{name})")
	@Options(useGeneratedKeys = true,keyProperty = "id")
	public int add(Type type);
	//������Ʒ����
	@Update("update type set name=#{name} where id=#{id}")
	public int update(Type type);
	
	
}
