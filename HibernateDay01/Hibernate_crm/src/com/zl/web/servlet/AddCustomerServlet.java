package com.zl.web.servlet;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;

import com.zl.domain.Customer;
import com.zl.service.CustomerService;
import com.zl.service.impl.CustomerServiceImpl;

public class AddCustomerServlet extends HttpServlet {

	private CustomerService customerService = new CustomerServiceImpl();

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		//获取表单数据
		Map<String, String[]> properties = request.getParameterMap();
		//封装实体
		Customer c = new Customer();
		try {
			BeanUtils.populate(c, properties);
		} catch (IllegalAccessException | InvocationTargetException e) {
			e.printStackTrace();
		}
		
		//传递请求到service层
		customerService.save(c);
		
		//重定向到客户列表
		response.sendRedirect(request.getContextPath()+"/ListCustomerServlet");
		
		
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
}