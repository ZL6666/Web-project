package com.zl.service.impl;

import com.zl.dao.Customerdao;
import com.zl.dao.impl.CustomerDaoImpl;
import com.zl.domain.Customer;
import com.zl.service.CustomerService;

public class CustomerServiceImpl implements CustomerService {

	private Customerdao dao = new CustomerDaoImpl();

	//保存客户
	public void save(Customer c) {
		//传递请求到dao层
		dao .save(c);
	}

}
