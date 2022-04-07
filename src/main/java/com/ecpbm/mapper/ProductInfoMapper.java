package com.ecpbm.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.*;
import org.apache.ibatis.mapping.FetchType;
import com.ecpbm.mapper.provider.ProductInfoDynaSqlProvider;
import com.ecpbm.pojo.ProductInfo;

public interface ProductInfoMapper {
	//根据条件分页查询商品
	@Results({ @Result(id=true, column= "id",property= "id"),
		@Result(column= "code",property= "code"),
		@Result(column= "name",property= "name"),
		@Result(column= "brand",property= "brand"),
		@Result(column= "pic",property= "pic"),
		@Result(column= "num",property= "num"),
		@Result(column= "price",property= "price"),
		@Result(column= "intro",property= "intro"),
		@Result(column= "status",property= "status"),
		@Result(column= "tid",property= "type",one=@One (select="com.ecpbm.mapper.TypeMapper.selectById", fetchType = FetchType.EAGER))
	})
	@SelectProvider(type=ProductInfoDynaSqlProvider.class, method="selectWithParam")
	List<ProductInfo> selectByPage(Map<String,Object> params);
	//条件查询商品总数
	@SelectProvider(type=ProductInfoDynaSqlProvider.class, method="count")
	Integer count(Map<String,Object> params);
	//添加商品
//	@Insert("insert into product_info (code,name,tid,brand,pic,num,price,intro,status)"
//			+"values(#{pi.code},#{pi.name},#{tid},#{pi.brand},#{pi.pic},#{pi.num},#{pi.price},#{pi.intro},#{pi.status})")
//	@Options(useGeneratedKeys = true,keyProperty ="id")

	@Insert("insert into product_info (code,name,tid,brand,pic,num,price,intro,status)"
			+"values(#{code},#{name},#{type.id},#{brand},#{pic},#{num},#{price},#{intro},#{status})")
	@Options(useGeneratedKeys = true, keyProperty = "id")
	int save(ProductInfo pi);
	// 修改商品
	@Update("update product_info set code=#{pi.code},name=#{pi.name},tid=#{pi.type.id},"
			+ "brand=#{pi.brand},pic=#{pi.pic},num=#{pi.num},price=#{pi.price},intro=#{pi.intro}," + "status=#{pi.status} where id=#{pi.id}")
	void edit(@Param("pi")ProductInfo pi,@Param("tid") Integer tid);

	// 更新商品状态
	@Update("update product_info set status=#{flag} where id in (${ids})")
	void updateStatus(@Param("ids") String ids, @Param("flag") int flag);

	// 获取在售商品列表
	@Select("select * from product_info where status=1")
	List<ProductInfo> getOnSaleProduct();

	// 根据产品id获取商品对象
	@Select("select * from product_info where id=#{id}")
	ProductInfo getProductInfoById(int id);
}
