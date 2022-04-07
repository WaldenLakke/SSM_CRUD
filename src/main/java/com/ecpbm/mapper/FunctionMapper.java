package com.ecpbm.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Select;

import com.ecpbm.pojo.Functions;

public interface FunctionMapper {
	@Select("select * from functions where id in (select fid from powers where aid=#{aid})")
	public List<Functions> selectByAdminId(Integer id);
}
