package com.ecpbm.controller;

import java.io.File;
import java.util.*;

import javax.servlet.http.HttpServletRequest;

import org.apache.ibatis.javassist.expr.NewArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.ecpbm.pojo.Pager;
import com.ecpbm.pojo.ProductInfo;
import com.ecpbm.service.ProductInfoService;


@Controller
@RequestMapping("/productinfo")
public class ProductInfoController {
	@Autowired
	private ProductInfoService productInfoService;
	
	@RequestMapping("/list")
	@ResponseBody
	public Map<String,Object> list(Integer page,Integer rows,ProductInfo productInfo)
	{
		Pager pager=new Pager();
		pager.setCurPage(page);
		pager.setPerPageRows(rows);
		Map<String, Object> params=new HashMap<>();
		params.put("productInfo", productInfo);
		int totalCount=productInfoService.count(params);
		List<ProductInfo> productionInfos=productInfoService.findProductInfo(productInfo, pager);
		Map<String, Object> result=new HashMap<>(2);
		result.put("count", totalCount);
		result.put("rows", productionInfos);
		return result;
	}
	
	@RequestMapping(value="/addProduct",
			consumes = "multipart/form-data;charset=UTF-8",
			produces ="text/html;charset=UTF-8")
	@ResponseBody
	public String addProduct(ProductInfo pi,@RequestParam(value="file",required = false) MultipartFile file,HttpServletRequest request)
	{
		String path=request.getSession().getServletContext().getRealPath("product_images");
		String fileName=file.getOriginalFilename();
		File targetFile =new File(path,fileName);
		File parentFile=new File(path);
		if(!targetFile.exists())
		{
			if(parentFile.exists()) parentFile.delete();
			targetFile.mkdirs();
		}
		try {
			file.transferTo(targetFile);
			pi.setPic(fileName);
			productInfoService.addProductInfo(pi);
			return "{\"success:\":\"true\",\"message\":\"��Ʒ��ӳɹ�\"}";
		} catch(Exception e)
		{
			return "{\"success:\":\"false\",\"message\":\"��Ʒ���ʧ��\"}";
		}
	}
	
	@PostMapping(value="/deleteProduct",produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String deleteProduct(@RequestParam("id") String id,@RequestParam("flag") String flag)
	{
		String str="";
		try {
			productInfoService.modifyStatus(id.substring(0, id.length()-1),Integer.parseInt(flag));
			str="{\"success:\":\"true\",\"message\":\"��Ʒɾ���ɹ�\"}";
		} catch(Exception e)
		{
			str="{\"success:\":\"true\",\"message\":\"��Ʒɾ��ʧ��\"}";
		}
		return str;
	}
	
	@PostMapping(value="/updateProduct",produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String updateProduct(ProductInfo pi,@RequestParam(value="file",required = false) MultipartFile file,HttpServletRequest request)
	{
		String path=request.getSession().getServletContext().getRealPath("product_images");
		String fileName=file.getOriginalFilename();
		File targetFile =new File(path,fileName);
		File parentFile=new File(path);
		if(!targetFile.exists())
		{
			if(parentFile.exists()) parentFile.delete();
			targetFile.mkdirs();
		}
		try {
			System.out.println("�ļ��ƶ�ǰ");
			file.transferTo(targetFile);
			System.out.println("�ļ��ƶ���");
			pi.setPic(fileName);
			System.out.println("����pi��ͼƬ·��������ɡ�");
			productInfoService.modifyProductInfo(pi);
			return "{\"success:\":\"true\",\"message\":\"��Ʒ�޸ĳɹ�\"}";
		} catch(Exception e)
		{
			e.printStackTrace();
			return "{\"success:\":\"false\",\"message\":\"��Ʒ�޸�ʧ��\"}";
		}
	}

	@RequestMapping("/getOnSaleProduct")
	@ResponseBody
	public List<ProductInfo> getOnSaleProduct()
	{
		List<ProductInfo> productions=productInfoService.getOnSaleProduct();
		return productions;
	}
	
	@RequestMapping("/getPriceById")
	@ResponseBody
	public String getPriceById(@RequestParam("pid") String pid)
	{
		if(pid!=null&&!pid.equals("")) 
		{
		ProductInfo productInfo=productInfoService.getProductInfoById(Integer.parseInt(pid));
		return productInfo.getPrice()+"";
		}
		return "";
	}

	
}
