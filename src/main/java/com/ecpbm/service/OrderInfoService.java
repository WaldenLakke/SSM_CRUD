package com.ecpbm.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.ecpbm.mapper.OrderInfoMapper;
import com.ecpbm.pojo.OrderDetail;
import com.ecpbm.pojo.OrderInfo;
import com.ecpbm.pojo.Pager;

@Component
@Transactional(propagation=Propagation.REQUIRED,isolation=Isolation.DEFAULT)
public class OrderInfoService {
	@Autowired OrderInfoMapper orderInfoMapper;
	public List<OrderInfo> findOrderInfo(OrderInfo orderInfo, Pager pager) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("orderInfo", orderInfo);
		int recordCount = orderInfoMapper.count(params);
		pager.setRowCount(recordCount);
		if (recordCount > 0) {
			params.put("pager", pager);
		}
		return orderInfoMapper.selectByPage(params);
	} 
	
	public Integer count(Map<String, Object> params) {
		return orderInfoMapper.count(params);
	}

	public int addOrderInfo(OrderInfo oi) {
		return orderInfoMapper.saveOrderInfo(oi);
	}

	public int addOrderDetail(OrderDetail od) {
		return orderInfoMapper.saveOrderDetail(od);
	}

	public OrderInfo getOrderInfoById(int id) {
		return orderInfoMapper.getOrderInfoById(id);
	}

	public List<OrderDetail> getOrderDetailByOid(int oid) {
		return orderInfoMapper.getOrderDetailByOid(oid);
	}

	public int deleteOrder(int id) {
		int result = 1;
		try {
			orderInfoMapper.deleteOrderDetail(id);
			orderInfoMapper.deleteOrderInfo(id);
		} catch (Exception e) {
			result = 0;
		}
		return result;
	}

}
