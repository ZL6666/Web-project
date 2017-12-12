package com.zl.service;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;

import com.zl.domain.Customer;

public interface CustomerService {
	
	//保存客户
	void save(Customer c);
	//查询所有客户
	List<Customer> getAll();
	//根据条件查询客户
	List<Customer> getAll(DetachedCriteria dc);

}
