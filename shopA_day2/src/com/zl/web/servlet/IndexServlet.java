package com.zl.web.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.zl.domain.Category;
import com.zl.domain.Product;
import com.zl.service.ProductService;

public class IndexServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		ProductService service = new ProductService();
		//准备热门商品---List<Product>
		List<Product> hotProdcutList = service.findHotProductList();
		//准备最新商品---List<Product>
		List<Product> newProdcutList = service.findNewProductList();
//		//获取商品类别
//		List<Category> categoryList = service.findAllCategory();
		
		request.setAttribute("hotProdcutList", hotProdcutList);
		request.setAttribute("newProdcutList", newProdcutList);
//		request.setAttribute("categoryList", categoryList);
		
		request.getRequestDispatcher("/index.jsp").forward(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
}
