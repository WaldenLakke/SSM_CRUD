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
	//��ҳ��ȡ����
	@Results({
		@Result(column="uid",property="ui",one=@One(select="com.ecpbm.mapper.UserInfoMapper.getUserInfoById",fetchType = FetchType.EAGER))
	})
	@SelectProvider(type = OrderInfoDynaSqlProvider.class, method = "selectWithParam")
	List<OrderInfo> selectByPage(Map<String, Object> params);
	
	// ����������ѯ��������
	@SelectProvider(type = OrderInfoDynaSqlProvider.class, method = "count")
	Integer count(Map<String, Object> params);
	
	// ���涩������
	@Insert("insert into order_info(uid,status,ordertime,orderprice) "
			+ "values(#{uid},#{status},#{ordertime},#{orderprice})")
	@Options(useGeneratedKeys = true, keyProperty = "id")
	int saveOrderInfo(OrderInfo oi);
	
	// ���涩����ϸ
	@Insert("insert into order_detail(oid,pid,num) values(#{oid},#{pid},#{num})")
	@Options(useGeneratedKeys = true, keyProperty = "id")
	int saveOrderDetail(OrderDetail od);
	
	//������Ż�ȡ��������
	@Results({
		@Result(column="uid",property="ui",one=@One(select="com.ecpbm.mapper.UserInfoMapper.getUserInfoById",fetchType = FetchType.EAGER))
	})
	@Select("select from order_info where id=#{id}")
	public OrderInfo getOrderInfoById(@Param("id") int id);
	
	// ���ݶ�����Ż�ȡ������ϸ
	@Results({
			@Result(column = "pid", property = "pi", one = @One(select = "com.ecpbm.mapper.ProductInfMapper.getProductInfoById", 
					fetchType = FetchType.EAGER)) })
	@Select("select * from order_detail where oid = #{oid}")
	public List<OrderDetail> getOrderDetailByOid(@Param("oid") int oid);
	
	//���ݶ������ɾ�������¼
	@Delete("delete from order_id where id=#{id}")
	public int deleteOrderInfo(int id);
	
	// ���ݶ�����ţ�ɾ��������ϸ��¼
	@Delete("delete from order_detail where oid=#{id}")
	public int deleteOrderDetail(int id);
}
