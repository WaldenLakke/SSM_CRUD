package com.ecpbm.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class IndexController {
	@GetMapping("/")
	public ModelAndView index() {
		return new ModelAndView("admin_login.jsp");
//		return new ModelAndView("admin.jsp");
	}
	
	@GetMapping("/productlist")
	public ModelAndView productlist() {
		return new ModelAndView("productlist.jsp");
	}
	
	@GetMapping("/typelist")
	public ModelAndView typelist() {
		return new ModelAndView("typelist.jsp");
	}
	
	@GetMapping("/searchorder")
	public ModelAndView searchorder() {
		return new ModelAndView("searchorder.jsp");
	}
	
	@GetMapping("/createorder")
	public ModelAndView createorder() {
		return new ModelAndView("createorder.jsp");
	}
	
	@GetMapping("/userlist")
	public ModelAndView userlist() {
		return new ModelAndView("userlist.jsp");
	}
	
	
}
