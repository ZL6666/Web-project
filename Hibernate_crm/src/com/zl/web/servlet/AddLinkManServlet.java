package com.zl.web.servlet;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;

import com.zl.domain.LinkMan;
import com.zl.service.LinkManService;
import com.zl.service.impl.LinkManServiceImpl;

public class AddLinkManServlet extends HttpServlet {

	private LinkManService lms = new LinkManServiceImpl();

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		//获取表单数据
		Map<String, String[]> properties = request.getParameterMap();
		LinkMan lm = new LinkMan();
		try {
			BeanUtils.populate(lm, properties);
		} catch (IllegalAccessException | InvocationTargetException e) {
			e.printStackTrace();
		}
		//传递请求到service层
		lms .save(lm);
		//转发
		response.sendRedirect(request.getContextPath()+"/ListLinkManServlet");
		
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
}