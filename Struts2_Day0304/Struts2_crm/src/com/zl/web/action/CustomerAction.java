package com.zl.web.action;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.ServletActionContext;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
import com.zl.domain.Customer;
import com.zl.service.CustomerService;
import com.zl.service.impl.CustomerServiceImpl;

public class CustomerAction extends ActionSupport implements ModelDriven<Customer>{
	
	CustomerService service = new CustomerServiceImpl();
	private Customer customer = new Customer();
	
	public String list() throws Exception {
		//获取参数com.zl.dao.impl
		String cust_name = ServletActionContext.getRequest().getParameter("cust_name");
		//创建离线条件
		DetachedCriteria dc = DetachedCriteria.forClass(Customer.class);
		//判断输入条件是否为空
		if(StringUtils.isNotBlank(cust_name)){
			//封装条件
			dc.add(Restrictions.like("cust_name","%"+cust_name+"%"));
		}
		List<Customer> list = service.getAll(dc);
		//ServletActionContext.getRequest().setAttribute("list", list);
		// 放到ActionContext
		ActionContext.getContext().put("list", list);
		return "list";
	}
	
	public String add() throws Exception {
		//1 调用Service
		service.save(customer);
		//2 重定向到列表action方法
		return "toList";
	}



	public Customer getModel() {
		return customer;
	}
	
}
