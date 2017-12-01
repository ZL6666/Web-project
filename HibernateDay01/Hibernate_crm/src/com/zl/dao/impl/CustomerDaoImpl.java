package com.zl.dao.impl;

import org.hibernate.Session;
import org.hibernate.Transaction;

import com.zl.dao.Customerdao;
import com.zl.domain.Customer;
import com.zl.utils.HibernateUtils;

public class CustomerDaoImpl implements Customerdao {

	//保存客户
	public void save(Customer c) {
		//获取session
		Session session = HibernateUtils.openSession();
		//打开事务
		Transaction tx = session.beginTransaction();
		//执行
		session.save(c);
		//提交事务
		tx.commit();
		//关闭资源
		session.close();

	}

}
