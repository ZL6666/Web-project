package com.zl.service.impl;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.DetachedCriteria;

import com.zl.dao.CustomerDao;
import com.zl.dao.impl.CustomerDaoImpl;
import com.zl.domain.Customer;
import com.zl.service.CustomerService;
import com.zl.utils.HibernateUtils;

public class CustomerServiceImpl implements CustomerService {

	private CustomerDao dao = new CustomerDaoImpl();

	// 保存客户
	public void save(Customer c) {
		// 获取session
		Session session = HibernateUtils.getCurrentSession();
		// 打开事务
		Transaction tx = session.beginTransaction();
		try {
			dao.save(c);
		} catch (Exception e) {
			e.printStackTrace();
			tx.rollback();
		}
		// 提交事务
		tx.commit();
	}

	// 查询所有用户
	public List<Customer> getAll() {
		// 获取session
		Session session = HibernateUtils.getCurrentSession();
		// 打开事务
		Transaction tx = session.beginTransaction();
		List<Customer> list=null;
		try {
			list = dao.getAll();
		} catch (Exception e) {
			e.printStackTrace();
			tx.rollback();
		}
		
		// 提交事务
		tx.commit();
		return list;
	}

	//根据条件查询客户
	public List<Customer> getAll(DetachedCriteria dc) {
		Session session = HibernateUtils.getCurrentSession();
		Transaction tx = session.beginTransaction();
		List<Customer> list = dao.getAll(dc);
		tx.commit();
		return list;
	}

}
