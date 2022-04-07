package com.ecpbm.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.ecpbm.mapper.AdminInfoMapper;
import com.ecpbm.pojo.AdminInfo;
import com.ecpbm.pojo.Functions;

@Component
@Transactional(propagation=Propagation.REQUIRED,isolation=Isolation.DEFAULT)
public class AdminInfoService {
	@Autowired AdminInfoMapper adminInfoMapper;
	public AdminInfo login(AdminInfo ai)
	{
		return adminInfoMapper.selectByNameAndPwd(ai);
	}
	
	public AdminInfo getAdminInfoAndFunctions(Integer id)
	{
		//½ö¹©µ÷ÊÔ
//		AdminInfo debugAdminInfo=adminInfoMapper.selectById(id);
//		System.out.println(debugAdminInfo.getId());
//		System.out.println(debugAdminInfo.getName());
//		for(Functions f:debugAdminInfo.getFs())
//		{
//			System.out.println(f.getName());
//		}
		//
		return adminInfoMapper.selectById(id);
	}
}
