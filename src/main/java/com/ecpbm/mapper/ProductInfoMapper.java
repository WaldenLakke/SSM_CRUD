package com.ecpbm.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.*;
import org.apache.ibatis.mapping.FetchType;
import com.ecpbm.mapper.provider.ProductInfoDynaSqlProvider;
import com.ecpbm.pojo.ProductInfo;

public interface ProductInfoMapper {
	//����������ҳ��ѯ��Ʒ
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
	//������ѯ��Ʒ����
	@SelectProvider(type=ProductInfoDynaSqlProvider.class, method="count")
	Integer count(Map<String,Object> params);
	//�����Ʒ
//	@Insert("insert into product_info (code,name,tid,brand,pic,num,price,intro,status)"
//			+"values(#{pi.code},#{pi.name},#{tid},#{pi.brand},#{pi.pic},#{pi.num},#{pi.price},#{pi.intro},#{pi.status})")
//	@Options(useGeneratedKeys = true,keyProperty ="id")

	@Insert("insert into product_info (code,name,tid,brand,pic,num,price,intro,status)"
			+"values(#{code},#{name},#{type.id},#{brand},#{pic},#{num},#{price},#{intro},#{status})")
	@Options(useGeneratedKeys = true, keyProperty = "id")
	int save(ProductInfo pi);
	// �޸���Ʒ
	@Update("update product_info set code=#{pi.code},name=#{pi.name},tid=#{pi.type.id},"
			+ "brand=#{pi.brand},pic=#{pi.pic},num=#{pi.num},price=#{pi.price},intro=#{pi.intro}," + "status=#{pi.status} where id=#{pi.id}")
	void edit(@Param("pi")ProductInfo pi,@Param("tid") Integer tid);

	// ������Ʒ״̬
	@Update("update product_info set status=#{flag} where id in (${ids})")
	void updateStatus(@Param("ids") String ids, @Param("flag") int flag);

	// ��ȡ������Ʒ�б�
	@Select("select * from product_info where status=1")
	List<ProductInfo> getOnSaleProduct();

	// ���ݲ�Ʒid��ȡ��Ʒ����
	@Select("select * from product_info where id=#{id}")
	ProductInfo getProductInfoById(int id);
}
