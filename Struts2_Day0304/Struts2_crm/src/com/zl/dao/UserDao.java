package com.zl.dao;

import com.zl.domain.User;

public interface UserDao {

	//根据编码查询用户
	User getByUserCode(String user_code);

}
