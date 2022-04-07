package com.ecpbm.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.ecpbm.pojo.AdminInfo;
import com.ecpbm.pojo.Functions;
import com.ecpbm.pojo.TreeNode;
import com.ecpbm.service.AdminInfoService;
import com.ecpbm.util.JsonFactory;

@Controller
@RequestMapping("/admininfo")
public class AdminInfoController {
	@Autowired 
	private AdminInfoService adminInfoService;
	@RequestMapping(value="/login",produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String login(HttpSession session,AdminInfo ai)
	{
		AdminInfo adminInfo=adminInfoService.login(ai);
		if(adminInfo!=null && adminInfo.getName()!=null)
		{
			if(adminInfoService.getAdminInfoAndFunctions(adminInfo.getId()).getFs().size()>0)
			{
				session.setAttribute("admin", adminInfo);
				return "{\"success\":\"true\",\"message\":\"登陆成功\"}";
			}
			else {
				return "{\"success\":\"false\",\"message\":\"您没有权限，请联系超级管理员设置权限\"}";
			}
		}
		else {
			return "{\"success\":\"false\",\"message\":\"登陆失败\"}";
		}
		
	}
	
	@RequestMapping("/getTree")
	@ResponseBody
	public List<TreeNode> getTree(HttpSession session)
	{
		int adminid=((AdminInfo)(session.getAttribute("admin"))).getId();
		AdminInfo adminInfo=adminInfoService.getAdminInfoAndFunctions(adminid);
		List<TreeNode> nodes=new ArrayList<>();
		List<Functions> functionlList=adminInfo.getFs();
		Collections.sort(functionlList);
		for(Functions functions:functionlList)
		{
			TreeNode treeNode=new TreeNode();
			treeNode.setId(functions.getId());
			treeNode.setFid(functions.getParentid());
			treeNode.setText(functions.getName());
			nodes.add(treeNode);
		}
		List<TreeNode> treeNodes=JsonFactory.buildtree(nodes,0);
		return treeNodes;
		
	}
	
	@GetMapping("/logout")
	@ResponseBody
	public String logout(HttpSession session)
	{
		session.removeAttribute("admin");
		return "{\"success\":\"true\",\"message\":\"注销成功\"}";
	}
	
	@GetMapping("/admin")
	@ResponseBody
	public ModelAndView admin(HttpSession session)
	{
		AdminInfo ai=(AdminInfo)(session.getAttribute("admin"));
		Map<String, Object> model = new HashMap<>();
		if (ai != null) {
			model.put("admin", ai);
		}
		return new ModelAndView("admin.jsp",model);
	}
	
}
