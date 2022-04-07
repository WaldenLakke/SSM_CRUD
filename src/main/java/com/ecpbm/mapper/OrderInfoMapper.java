package com.ecpbm.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.One;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.mapping.FetchType;

import com.ecpbm.pojo.OrderDetail;
import com.ecpbm.pojo.OrderInfo;
import com.ecpbm.mapper.provider.OrderInfoDynaSqlProvider;

public interface OrderInfoMapper {
	//分页获取订单
	@Results({
		@Result(column="uid",property="ui",one=@One(select="com.ecpbm.mapper.UserInfoMapper.getUserInfoById",fetchType = FetchType.EAGER))
	})
	@SelectProvider(type = OrderInfoDynaSqlProvider.class, method = "selectWithParam")
	List<OrderInfo> selectByPage(Map<String, Object> params);
	
	// 根据条件查询订单总数
	@SelectProvider(type = OrderInfoDynaSqlProvider.class, method = "count")
	Integer count(Map<String, Object> params);
	
	// 保存订单主表
	@Insert("insert into order_info(uid,status,ordertime,orderprice) "
			+ "values(#{uid},#{status},#{ordertime},#{orderprice})")
	@Options(useGeneratedKeys = true, keyProperty = "id")
	int saveOrderInfo(OrderInfo oi);
	
	// 保存订单明细
	@Insert("insert into order_detail(oid,pid,num) values(#{oid},#{pid},#{num})")
	@Options(useGeneratedKeys = true, keyProperty = "id")
	int saveOrderDetail(OrderDetail od);
	
	//订单编号获取订单对象
	@Results({
		@Result(column="uid",property="ui",one=@One(select="com.ecpbm.mapper.UserInfoMapper.getUserInfoById",fetchType = FetchType.EAGER))
	})
	@Select("select from order_info where id=#{id}")
	public OrderInfo getOrderInfoById(@Param("id") int id);
	
	// 根据订单编号获取订单明细
	@Results({
			@Result(column = "pid", property = "pi", one = @One(select = "com.ecpbm.mapper.ProductInfMapper.getProductInfoById", 
					fetchType = FetchType.EAGER)) })
	@Select("select * from order_detail where oid = #{oid}")
	public List<OrderDetail> getOrderDetailByOid(@Param("oid") int oid);
	
	//根据订单编号删除主表记录
	@Delete("delete from order_id where id=#{id}")
	public int deleteOrderInfo(int id);
	
	// 根据订单编号，删除订单明细记录
	@Delete("delete from order_detail where oid=#{id}")
	public int deleteOrderDetail(int id);
}
