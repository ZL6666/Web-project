package com.zl.test;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.junit.Test;

import com.zl.domain.Customer;

public class Demo {
	
	@Test
	//保存客户
	public void fun1(){
		Configuration conf = new Configuration().configure();
		
		SessionFactory sessionFactory = conf.buildSessionFactory();
		
		Session session = sessionFactory.openSession();
		
		Transaction tx = session.beginTransaction();
		//--------------------------------------------------
		Customer c = new Customer();
		c.setCust_name("谷歌公司");
		session.save(c);
		//--------------------------------------------------
		tx.commit();
		session.close();
		sessionFactory.close();
		
	}
}
