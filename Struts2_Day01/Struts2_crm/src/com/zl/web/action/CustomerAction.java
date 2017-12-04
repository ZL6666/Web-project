package com.zl.web.action;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.ServletActionContext;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import com.opensymphony.xwork2.ActionSupport;
import com.zl.domain.Customer;
import com.zl.service.CustomerService;
import com.zl.service.impl.CustomerServiceImpl;

public class CustomerAction extends ActionSupport{
	
	CustomerService service = new CustomerServiceImpl();
	
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
		ServletActionContext.getRequest().setAttribute("list", list);
		return "list";
	}
	
}
