package com.zl.web.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import com.zl.domain.Customer;
import com.zl.service.CustomerService;
import com.zl.service.impl.CustomerServiceImpl;

public class ListCustomerServlet extends HttpServlet {

	private CustomerService service = new CustomerServiceImpl();

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		//获得表单数据
		String cust_name = request.getParameter("cust_name");
		//创建离线条件对象
		DetachedCriteria dc = DetachedCriteria.forClass(Customer.class);
		
		//判断输入的条件是否为空
		if(cust_name!=null&&!"".equals(cust_name)){
			//封装条件
			dc.add(Restrictions.like("cust_name", "%"+cust_name+"%"));
		}
		//传递请求到service层
		List<Customer> list = service .getAll(dc);
		//放到request域中
		request.setAttribute("list", list);
		//转发到列表页面
		request.getRequestDispatcher("/jsp/customer/list.jsp").forward(request, response);
		
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
}