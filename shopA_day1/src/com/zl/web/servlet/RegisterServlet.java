package com.zl.web.servlet;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Map;

import javax.mail.MessagingException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.Converter;

import com.zl.domain.User;
import com.zl.service.UserService;
import com.zl.utils.CommonsUtils;
import com.zl.utils.MailUtils;

public class RegisterServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		request.setCharacterEncoding("UTF-8");
		//获取表单数据
		Map<String, String[]> properties = request.getParameterMap();
		User user = new User();
		try {
			//指定一个类型转换器，将String转换成Date
			ConvertUtils.register(new Converter() {
				
				@Override
				public Object convert(Class clazz, Object value) {
					SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
					Date parse = null;
					try {
						parse = (Date) format.parse(value.toString());
					} catch (ParseException e) {
						e.printStackTrace();
					}
					return parse;
				}
			}, Date.class);
			
			BeanUtils.populate(user, properties);
		} catch (IllegalAccessException | InvocationTargetException e) {
			e.printStackTrace();
		}
		
		//private String uid;
		user.setUid(CommonsUtils.getUUID());
		//private String telephone;
		user.setTelephone(null);
		//private int state;//是否激活
		user.setState(0);
		//private String code;//激活码
		String activeCode = CommonsUtils.getUUID();
		user.setCode(activeCode);
		
		//传递请求到service层
		UserService service = new UserService();
		boolean isRegisterSuccess = service.register(user);
		
		if(isRegisterSuccess){
			//发送激活邮件
			String emailMsg = "恭喜您注册成功，请点击下面的连接进行激活账户"
					+ "<a href='http://localhost:8080/shopA/active?activeCode="+activeCode+"'>"
							+ "http://localhost:8080/shopA/active?activeCode="+activeCode+"</a>";
			try {
				MailUtils.sendMail(user.getEmail(), emailMsg);
			} catch (MessagingException e) {
				e.printStackTrace();
			}
			
			//注册成功，跳转到登陆页面
			response.sendRedirect(request.getContextPath()+"/registerSuccess.jsp");
			
		}else{
			//注册失败
			response.sendRedirect(request.getContextPath()+"/registerFail.jsp");
		}
		
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
}