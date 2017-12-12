package com.zl.service.impl;

import org.hibernate.Session;
import org.hibernate.Transaction;

import com.zl.dao.CustomerDao;
import com.zl.dao.LinkManDao;
import com.zl.dao.impl.CustomerDaoImpl;
import com.zl.dao.impl.LinkManDaoImpl;
import com.zl.domain.Customer;
import com.zl.domain.LinkMan;
import com.zl.service.LinkManService;
import com.zl.utils.HibernateUtils;

public class LinkManServiceImpl implements LinkManService {

	private CustomerDao cd = new CustomerDaoImpl();
	private LinkManDao lmd = new LinkManDaoImpl();

	public void save(LinkMan lm) {
		
		Session session = HibernateUtils.getCurrentSession();
		Transaction tx = session.beginTransaction();
		//根据输入的id获取客户
		Long cust_id = lm.getCust_id();
		Customer c = cd .getById(cust_id);
		//将客户放入LinkMan中表达关系
		lm.setCustomer(c);
		//保存联系人
		lmd .save(lm);
		
		
		tx.commit();
	}

}
