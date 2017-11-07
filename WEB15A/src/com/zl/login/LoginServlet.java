package com.zl.login;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;

import com.zl.domain.User;
import com.zl.utils.DataSourceUtils;

public class LoginServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		//获取表单数据
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		
		//调用方法，查询用户名和密码是否正确
		User user = login(username,password);
		
		if(user!=null){
			//用户名和密码正确
			//登陆成功，重定向跳转到网站首页
			response.sendRedirect(request.getContextPath());
		}else{
			//用户名或密码错误
			//跳转到login.jsp页面
			request.setAttribute("loginInfo", "用户名或密码错误");
			//使用转发跳转
			request.getRequestDispatcher("/login.jsp").forward(request, response);
		}
		
		
		
	}
	
	public User login(String username,String password){
		QueryRunner qr = new QueryRunner(DataSourceUtils.getDataSource());
		String sql = "select * from user where username=? and password=?";
		User user = null;
		try {
			user = qr.query(sql, new BeanHandler<User>(User.class), username,password);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return user;
	}
	

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
}