package com.zl.service;

import java.sql.SQLException;

import com.zl.dao.UserDao;
import com.zl.domain.User;

public class UserService {

	public boolean register(User user) {
		UserDao dao = new UserDao();
		int row = 0;
		try {
			row = dao.register(user);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return row>0?true:false;
	}
	
	//激活码
	public void active(String activeCode) {
		UserDao dao = new UserDao();
		try {
			dao.active(activeCode);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	//校验 用户名是否存在
	public boolean checkUsername(String username) {
		UserDao dao = new UserDao();
		Long isExist = 0L;
		try {
			isExist = dao.checkUsername(username);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	
		return isExist>0?true:false;
	}

}
