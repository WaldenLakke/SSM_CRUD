package com.ecpbm.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.ecpbm.mapper.ProductInfoMapper;
import com.ecpbm.pojo.Pager;
import com.ecpbm.pojo.ProductInfo;

@Component
@Transactional(propagation=Propagation.REQUIRED,isolation=Isolation.DEFAULT)
public class ProductInfoService {
	@Autowired ProductInfoMapper productInfoMapper;
	public List<ProductInfo> findProductInfo(ProductInfo productInfo,Pager pager)
	{
		Map<String, Object> params=new HashMap<>();
		params.put("productInfo", productInfo);
		int recordCount=productInfoMapper.count(params);
		pager.setRowCount(recordCount);
		if(recordCount>0) params.put("pager", pager);
		return productInfoMapper.selectByPage(params);
	}
	
	public Integer count(Map<String, Object> params)
	{
		return productInfoMapper.count(params);
	}
	
	public void addProductInfo(ProductInfo productInfo)
	{
//		//
//		System.out.print("进入Service");
//		if(productInfo==null) System.out.println("没有pi");
//		System.out.println("有pi");
//		System.out.println(productInfo.getCode());
//		System.out.println(productInfo.getType().getId());
//		//
		productInfoMapper.save(productInfo);
	}
	
	public void modifyProductInfo(ProductInfo productInfo)
	{
//		//
//		System.out.print("进入Service");
//		if(productInfo==null) System.out.println("没有pi");
//		System.out.println("有pi");
//		System.out.println(productInfo.getCode());
//		System.out.println(productInfo.getType().getId());
//		//
		productInfoMapper.edit(productInfo,productInfo.getType().getId());
	}
	
	public void modifyStatus(String ids,int flag)
	{
		productInfoMapper.updateStatus(ids, flag);
	}
	
	public List<ProductInfo> getOnSaleProduct(){
		return productInfoMapper.getOnSaleProduct();
	}
	
	public ProductInfo getProductInfoById(int id) {
		return productInfoMapper.getProductInfoById(id);
	}
}

