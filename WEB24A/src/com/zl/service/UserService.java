package com.zl.service;

import java.sql.SQLException;

import com.zl.dao.UserDao;
import com.zl.domain.User;

public class UserService {

	public User login(String username, String password) throws SQLException {
		//传递请求到dao层
		UserDao dao = new UserDao();
		return dao.login(username,password);
	}

}
