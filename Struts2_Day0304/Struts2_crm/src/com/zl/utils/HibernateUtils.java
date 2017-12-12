package com.zl.utils;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateUtils {
	
	private static SessionFactory sessionFactory;
	static{
		Configuration conf = new Configuration().configure();
		sessionFactory = conf.buildSessionFactory();
	}

	public static Session openSession() {
		
		Session session = sessionFactory.openSession();
		return session;
	}
	
	public static Session getCurrentSession(){
		Session session = sessionFactory.getCurrentSession();
		return session;
	}
}
