package com.zl.web.servlet;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.zl.domain.User;
import com.zl.service.UserService;

public class LoginServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//获取表单
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		
		HttpSession session = request.getSession();
		
		//传递请求到service层
		UserService service = new UserService();
		User user = null;
		try {
			user = service.login(username,password);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		//判断是否登陆成功
		if(user!=null){
			//登陆成功
			//判断是否勾选自动登陆
			String autoLogin = request.getParameter("autoLogin");
			if(autoLogin!=null){
				//勾选了自动登陆
				//创建cookie
				Cookie cookie_username = new Cookie("cookie_username",username);
				Cookie cookie_password = new Cookie("cookie_password",password);
				//设置cookie持久化时间
				cookie_username.setMaxAge(60*60);
				cookie_password.setMaxAge(60*60);
				//设置cookie的携带路径
				cookie_username.setPath(request.getContextPath());
				cookie_password.setPath(request.getContextPath());
				//发送cookie
				response.addCookie(cookie_username);
				response.addCookie(cookie_password);
				
			}
			
			
			session.setAttribute("user", user);
			response.sendRedirect(request.getContextPath());
		}else{
			//登陆失败
			session.setAttribute("loginInfo", "用户名或密码错误");
			request.getRequestDispatcher("/login.jsp").forward(request, response);
		}
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
}