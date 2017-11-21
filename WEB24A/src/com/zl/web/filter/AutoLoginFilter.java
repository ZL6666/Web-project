package com.zl.web.filter;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.zl.domain.User;
import com.zl.service.UserService;

public class AutoLoginFilter implements Filter {

	

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse res = (HttpServletResponse) response;
		HttpSession session = req.getSession();
		
		//获取cookie
		String cookie_username = null;
		String cookie_password = null;
		Cookie[] cookies = req.getCookies();
		if(cookies!=null){
			for(Cookie cookie:cookies){
				//获得名字是cookie_username和cookie_password
				if("cookie_username".equals(cookie.getName())){
					cookie_username = cookie.getValue();
				}
				if("cookie_password".equals(cookie.getName())){
					cookie_password = cookie.getValue();
				}
			}
		}
		
		//判断username和password是否为空
		if(cookie_username!=null&&cookie_password!=null){
			//登陆的代码
			//传递请求到service层
			UserService service = new UserService();
			User user = null;
			try {
				user = service.login(cookie_username,cookie_password);
			} catch (SQLException e) {
				e.printStackTrace();
			}
			//将登录的用户的user对象存到session中
			session.setAttribute("user", user);
		}
		
		chain.doFilter(req, res);
		
		
		
		
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		
	}
	
	@Override
	public void destroy() {
		
	}

}
