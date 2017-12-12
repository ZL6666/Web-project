package com.zl.web.action;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
import com.zl.domain.User;
import com.zl.service.UserService;
import com.zl.service.impl.UserServiceImpl;

public class UserAction extends ActionSupport implements ModelDriven<User>{
	
	private User user = new User();
	private UserService service = new UserServiceImpl();
	
	
	
	
	public String login() throws Exception {
		//1 调用Service 执行登陆操作
		User u = service .login(user);
		//2 将返回的User对象放入session域作为登陆标识
		ActionContext.getContext().getSession().put("user", u);
		//3 重定向到项目的首页
		return "toHome";
	}



	public User getModel() {
		return user;
	}

}
