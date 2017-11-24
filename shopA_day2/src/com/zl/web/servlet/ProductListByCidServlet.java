package com.zl.web.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.zl.domain.PageBean;
import com.zl.domain.Product;
import com.zl.service.ProductService;

public class ProductListByCidServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		ProductService service = new ProductService();
		String cid = request.getParameter("cid");
		
		//获取当前页
		String currentPageStr = request.getParameter("currentPage");
		if(currentPageStr==null) currentPageStr = "1";
		int currentPage = Integer.parseInt(currentPageStr);
		//每页显示条数
		int currentCount = 12;
		
		PageBean pageBean = service.findProductListByCid(cid,currentPage,currentCount);
		
		request.setAttribute("pageBean", pageBean);
		request.setAttribute("cid", cid);
		
		//定义一个记录历史商品信息的集合
		List<Product> historyProductList = new ArrayList<Product>();
		
		//获得客户端携带的cookie
		Cookie[] cookies = request.getCookies();
		if(cookies!=null){
			for(Cookie cookie:cookies){
				if("pids".equals(cookie.getName())){
					String pids = cookie.getValue();
					String[] split = pids.split("-");
					for(String pid:split){
						Product pro = service.findProductByPid(pid);
						historyProductList.add(pro);
					}
				}
			}
			
		}
		
		//将历史记录的集合放到域中
		request.setAttribute("historyProductList", historyProductList);
		
		request.getRequestDispatcher("/product_list.jsp").forward(request, response);
		
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
}