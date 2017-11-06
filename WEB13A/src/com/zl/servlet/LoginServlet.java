package com.zl.servlet;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;

import com.zl.domain.User;
import com.zl.utils.DataSourceUtils;

public class LoginServlet extends HttpServlet {
	
	@Override
	public void init() throws ServletException {
		int count = 0;
		this.getServletContext().setAttribute("count", count);
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//1.获取表单数据
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		//2.与数据库进行比较
		QueryRunner qr = new QueryRunner(DataSourceUtils.getDataSource());
		String sql = "select * from user where username=? and password=?";
		User user = null;
		try {
			user = qr.query(sql, new BeanHandler<User>(User.class), username,password);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		if(user!=null){
			//登陆成功
			ServletContext context = this.getServletContext();
			Integer count = (Integer) context.getAttribute("count");
			count++;
			response.getWriter().write(user.toString()+"you are"+count+"times login");
			context.setAttribute("count", count);
		}else{
			//登陆失败
			response.getWriter().write("username or password is error!!!");
		}
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
}