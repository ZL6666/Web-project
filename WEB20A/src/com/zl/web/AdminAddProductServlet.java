package com.zl.web;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;

import com.zl.domain.Product;
import com.zl.service.AdminProductService;

public class AdminAddProductServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		//获取表单数据
		Map<String, String[]> properties = request.getParameterMap();
		//封装数据
		Product product = new Product();
		try {
			BeanUtils.populate(product, properties);
		} catch (IllegalAccessException | InvocationTargetException e) {
			e.printStackTrace();
		}
		//手动封装数据
		//1）、private String pid;
		product.setPid(UUID.randomUUID().toString());
		//2）、private String pimage;
		product.setPimage("products/1/c_0012.jpg");
		//3）、private String pdate;//上架日期
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		String pdate = format.format(new Date());
		product.setPdate(pdate);
		//4）、private int pflag;//商品是否下载 0代表未下架
		product.setPflag(0);
		
		//到这里，数据已经全部准备好
		//传递请求到service层
		AdminProductService service = new AdminProductService();
		try {
			service.addProduct(product);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		//跳转到列表页面
		response.sendRedirect(request.getContextPath()+"/adminProductList");
		
		
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
}