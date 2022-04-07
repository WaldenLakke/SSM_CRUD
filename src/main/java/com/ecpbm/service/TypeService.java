package com.ecpbm.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.ecpbm.mapper.TypeMapper;
import com.ecpbm.pojo.Type;

@Component
@Transactional(propagation=Propagation.REQUIRED,isolation=Isolation.DEFAULT)
public class TypeService {
	@Autowired TypeMapper typeMapper;
	public List<Type> getAll(){
		return typeMapper.selectAll();
	}
	
	public void addType(Type type)
	{
		typeMapper.add(type);
	}
	
	public void updateType(Type type)
	{
		typeMapper.update(type);
	}
	 
	 
}
