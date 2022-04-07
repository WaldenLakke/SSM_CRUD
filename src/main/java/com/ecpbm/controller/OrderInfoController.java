package com.ecpbm.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.ecpbm.pojo.OrderDetail;
import com.ecpbm.pojo.OrderInfo;
import com.ecpbm.pojo.Pager;
import com.ecpbm.service.OrderInfoService;
import com.google.gson.JsonParseException;


@Controller
@RequestMapping("/orderinfo")
public class OrderInfoController {
	@Autowired
	private OrderInfoService orderInfoService;
	
	@RequestMapping("/commitOrder")
	@ResponseBody
	public String commitOrder(String inserted,String orderinfo) throws JsonParseException,JsonMappingException,IOException{
		try {
			ObjectMapper mapper=new ObjectMapper();
			mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
			mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
			OrderInfo oi=mapper.readValue(orderinfo, OrderInfo[].class)[0];
			orderInfoService.addOrderInfo(oi);
			List<OrderDetail> odList=mapper.readValue(inserted, new TypeReference<ArrayList<OrderDetail>>(){});
			for (OrderDetail od:odList)
			{
				od.setOid(oi.getId());
				orderInfoService.addOrderDetail(od);
			}
			return "success";
		} catch (Exception e) {
			return "failure";
		}
	}
	
	@RequestMapping("/list")
	@ResponseBody
	public Map<String, Object> list(Integer page,Integer rows,OrderInfo orderInfo)
	{
		Pager pager=new Pager();
		pager.setCurPage(page);
		pager.setPerPageRows(rows);
		Map<String, Object> params=new HashMap<>();
		params.put("orderInfo", orderInfo);
		int totalCount=orderInfoService.count(params);
		List<OrderInfo> orderInfos=orderInfoService.findOrderInfo(orderInfo, pager);
		Map<String, Object> result=new HashMap<>(2);
		result.put("count", totalCount);
		result.put("rows", orderInfos);
		return result;
	}
	
	@PostMapping(value="/deleteOrder",produces="text/html;charset=UTF-8")
	@ResponseBody
	public String deleteOrder(String oids)
	{
		String str="";
		try {
			oids=oids.substring(0,oids.length()-1);
			String[] ids=oids.split(",");
			for(String id:ids)
			{
				orderInfoService.deleteOrder(Integer.parseInt(id));
			}
			str="{\"success:\":\"true\",\"message\":\"¶©µ¥É¾³ý³É¹¦\"}";
		} catch (Exception e) {
			str="{\"success:\":\"false\",\"message\":\"¶©µ¥É¾³ýÊ§°Ü\"}";
		}
		return str;
	}
	
	@RequestMapping("/getOrderInfo")
	@ResponseBody
	public String getOrderInfo(String oid,Model model)
	{
		OrderInfo oi=orderInfoService.getOrderInfoById(Integer.parseInt(oid));
		model.addAttribute("oi",oi);
		return "orderDetail";
	}
	
	@RequestMapping("/getOrderDetails")
	@ResponseBody
	public List<OrderDetail> getOrderDetails(String oid) {
		List<OrderDetail> ods = orderInfoService.getOrderDetailByOid(Integer.parseInt(oid));
		for (OrderDetail od : ods) {
			od.setPrice(od.getPi().getPrice());
			od.setTotalprice(od.getPi().getPrice() * od.getNum());
		}
		return ods;
	}
	
	
}
