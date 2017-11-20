package com.zl.service;

import java.sql.SQLException;

import com.zl.dao.UserDao;

public class UserService {

	public boolean checkUsername(String username) throws SQLException {
		
		UserDao dao = new UserDao();
		Long isExist = dao.chectUsername(username);
		
		return isExist>0?true:false;
	}

}
