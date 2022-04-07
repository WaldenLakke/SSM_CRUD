package com.ecpbm.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.ecpbm.mapper.UserInfoMapper;
import com.ecpbm.pojo.Pager;
import com.ecpbm.pojo.UserInfo;

@Component
@Transactional(propagation=Propagation.REQUIRED,isolation=Isolation.DEFAULT)
public class UserInfoService {
	@Autowired UserInfoMapper userInfoMapper;
	
	public List<UserInfo> getValidUser(){
		return userInfoMapper.getValidUser();
	}
	
	public UserInfo getUserInfoById(int id)
	{
		return userInfoMapper.getUserInfoById(id);
	}
	
	public List<UserInfo> findUserInfo(UserInfo userInfo,Pager pager)
	{
		Map<String, Object> params=new HashMap<>();
		params.put("userInfo", userInfo);
		int recordCount=userInfoMapper.count(params);
		pager.setRowCount(recordCount);
		if(recordCount>0) params.put("pager", pager);
		return userInfoMapper.selectByPage(params);
	}
	
	public Integer count(Map<String, Object> params)
	{
		return userInfoMapper.count(params);
	}
	
	public void modifyStatus(String ids,int flag)
	{
		userInfoMapper.updateStatus(ids, flag);
	}
}
