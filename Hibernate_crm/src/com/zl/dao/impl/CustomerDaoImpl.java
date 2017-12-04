package com.zl.dao.impl;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.DetachedCriteria;

import com.zl.dao.CustomerDao;
import com.zl.domain.Customer;
import com.zl.utils.HibernateUtils;

public class CustomerDaoImpl implements CustomerDao {

	//保存客户
	public void save(Customer c) {
		//获取session
		Session session = HibernateUtils.getCurrentSession();
		//执行
		session.save(c);
	}

	//查询所有客户
	public List<Customer> getAll() {
		//获取session
		Session session = HibernateUtils.getCurrentSession();
		Criteria criteria = session.createCriteria(Customer.class);
		List list = criteria.list();
		return list;
	}

	//根据id查询客户
	public Customer getById(Long cust_id) {
		//获得session
		Session session = HibernateUtils.getCurrentSession();
		return session.get(Customer.class, cust_id);
	}

	//根据条件查询客户
	public List<Customer> getAll(DetachedCriteria dc) {
		Session session = HibernateUtils.getCurrentSession();
		//将离线对象关联到session
		Criteria criteria = dc.getExecutableCriteria(session);
		List list = criteria.list();
		return list;
	}

}
