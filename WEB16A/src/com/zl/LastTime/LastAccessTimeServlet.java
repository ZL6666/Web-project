package com.zl.LastTime;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LastAccessTimeServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//获取当前时间
		Date date = new Date();
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		String currentTime = format.format(date);
		
		//创建cookie
		Cookie cookie = new Cookie("lastAccessTime", currentTime);
		cookie.setMaxAge(60*10);
		response.addCookie(cookie);
		
		//获取客户端的cookie
		Cookie[] cookies = request.getCookies();
		String lastAccessTime = null;
		if(cookies!=null){
			for (Cookie coo : cookies) {
				if("lastAccessTime".equals(coo.getName())){
					lastAccessTime = coo.getValue();
				}
			}
		}
		
		//设置中文编码
		response.setContentType("text/html;charset=UTF-8");
		
		//判断是否第一次访问
		if(lastAccessTime==null){
			response.getWriter().write("您是第一次访问!!!");
		}else{
			response.getWriter().write("您上次的访问时间是"+lastAccessTime);
		}
		
		
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
}