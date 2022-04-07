package com.ecpbm.mapper;

import org.apache.ibatis.annotations.Many;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.mapping.FetchType;

import com.ecpbm.pojo.AdminInfo;

public interface AdminInfoMapper {
	//根据名字和密码查找管理员
	@Select("select * from admin_info where name=#{name} and pwd=#{pwd}")
	public AdminInfo selectByNameAndPwd(AdminInfo ai);
	//根据管理员id查找管理员及关联功能
	@Select("select * from admin_info where id=#{id}")
	@Results({ @Result(id=true, column= "id",property= "id"),
		@Result(column= "name",property= "name"),
		@Result(column= "pwd",property= "pwd"),
		@Result(column= "id",property= "fs",many=@Many(select="com.ecpbm.mapper.FunctionMapper.selectByAdminId", fetchType = FetchType.EAGER))
	})
	AdminInfo selectById(Integer id);
}
