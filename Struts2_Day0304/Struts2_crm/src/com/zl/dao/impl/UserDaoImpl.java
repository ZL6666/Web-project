package com.zl.dao.impl;

import org.hibernate.Query;
import org.hibernate.Session;

import com.zl.dao.UserDao;
import com.zl.domain.User;
import com.zl.utils.HibernateUtils;

public class UserDaoImpl implements UserDao{

	//用户用户码查询用户
	public User getByUserCode(String user_code) {
		
		//1.获得Session
		Session session = HibernateUtils.getCurrentSession();
		//2 书写HQL
		String sql="from User where user_code=?";
		//3 创建查询对象
		Query query = session.createQuery(sql);
		//4 设置参数
		query.setParameter(0, user_code);
		//5 执行查询
		User user = (User) query.uniqueResult();
		return user;
	}

}
