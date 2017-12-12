package com.zl.service.impl;

import com.zl.dao.UserDao;
import com.zl.dao.impl.UserDaoImpl;
import com.zl.domain.User;
import com.zl.service.UserService;
import com.zl.utils.HibernateUtils;

public class UserServiceImpl implements UserService {
	
	private UserDao ud = new UserDaoImpl();

	//登陆方法
	public User login(User user) {
		//打开事务
		HibernateUtils.getCurrentSession().beginTransaction();
		User existU = ud .getByUserCode(user.getUser_code());
		//提交事务
		HibernateUtils.getCurrentSession().getTransaction().commit();
		if(existU==null){
			//获得不到=>抛出异常提示用户名不存在
			throw new RuntimeException("用户名不存在!");
		}
		//2 比对密码是否一致
		if(!existU.getUser_password().equals(user.getUser_password())){
			//不一致=>抛出异常提示密码错误
			throw new RuntimeException("密码错误!");
		}
		//3 将数据库查询的User返回
		return existU;
	}

}
