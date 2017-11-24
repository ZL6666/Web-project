package com.zl.web.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.gson.Gson;
import com.zl.domain.Cart;
import com.zl.domain.CartItem;
import com.zl.domain.Category;
import com.zl.domain.PageBean;
import com.zl.domain.Product;
import com.zl.service.ProductService;
import com.zl.utils.JedisPoolUtils;

import redis.clients.jedis.Jedis;

public class ProductServlet extends BaseServlet {
	
	//清空购物车
	public void clearCart(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession();
		session.removeAttribute("cart");
		
		//跳转回cart.jsp
		response.sendRedirect(request.getContextPath()+"/cart.jsp");
	}
	
	//从购物车中删除商品
	public void delProFromCart(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String pid = request.getParameter("pid");
		//删除session中的购物车中的购物项集合中的item
		HttpSession session = request.getSession();
		Cart cart = (Cart) session.getAttribute("cart");
		if(cart!=null){
			Map<String, CartItem> cartItems = cart.getCartItems();
			//修改总价
			//旧总价
			double oldtotal = cart.getTotal();
			//新总价
			CartItem cartItem = cartItems.get(pid);
			double subtotal = cartItem.getSubtotal();
			double newtotal = oldtotal-subtotal;
			cart.setTotal(newtotal);
			
			//删除
			cartItems.remove(pid);
			cart.setCartItems(cartItems);
		}
		
		session.setAttribute("cart", cart);

		//跳转回cart.jsp
		response.sendRedirect(request.getContextPath()+"/cart.jsp");
	}
	
	//将商品添加到购物车
	public void addProductToCart(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		ProductService service = new ProductService();
		
		HttpSession session = request.getSession();
		
		//获取商品的pid
		String pid = request.getParameter("pid");
		//获取商品的购买数量
		String buyNumStr = request.getParameter("buyNum");
		int buyNum = Integer.parseInt(buyNumStr);
		
		//获取product对象
		Product product = service.findProductByPid(pid);
		//计算小计
		double subtotal = product.getShop_price()*buyNum;
		
		//封装CartItem
		CartItem item = new CartItem();
		item.setProduct(product);
		item.setBuyNum(buyNum);
		item.setSubtotal(subtotal);
		
		//获得购物车---判断是否在session中已经存在购物车
		Cart cart = (Cart) session.getAttribute("cart");
		if(cart==null){
			cart = new Cart();
		}
		
		//将购物项放到车中---key是pid
		//先判断购物车中是否已将包含此购物项了 ----- 判断key是否已经存在
		//如果购物车中已经存在该商品----将现在买的数量与原有的数量进行相加操作
		Map<String, CartItem> cartItems = cart.getCartItems();
		
		double newsubtotal = 0.0;
		
		if(cartItems.containsKey(pid)){
			//取出原有商品的数量
			CartItem cartItem = cartItems.get(pid);
			int oldBuyNum = cartItem.getBuyNum();
			
			//计算新的商品数量
			int newBuyNum = oldBuyNum+buyNum;
			cartItem.setBuyNum(newBuyNum);
			
			//计算小计
			//获取该商品的旧小计
			double oldSubtotal = cartItem.getSubtotal();
			//计算新小计
			newsubtotal = product.getShop_price()*buyNum;
			cartItem.setSubtotal(newsubtotal+oldSubtotal);
			
			//封装cart
			cart.setCartItems(cartItems);
		}else{
			//如果车中没有该商品
			cart.getCartItems().put(product.getPid(), item);
			newsubtotal = product.getShop_price()*buyNum;
		}
		
		//计算总计
		double total = cart.getTotal()+newsubtotal;
		cart.setTotal(total);
		
		//将车再次访问session
		session.setAttribute("cart", cart);
		
		//直接跳转到购物车页面
		response.sendRedirect(request.getContextPath()+"/cart.jsp");
	}
	
	// 根据cid查询商品
	public void productListByCid(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		ProductService service = new ProductService();
		String cid = request.getParameter("cid");

		// 获取当前页
		String currentPageStr = request.getParameter("currentPage");
		if (currentPageStr == null)
			currentPageStr = "1";
		int currentPage = Integer.parseInt(currentPageStr);
		// 每页显示条数
		int currentCount = 12;

		PageBean pageBean = service.findProductListByCid(cid, currentPage, currentCount);

		request.setAttribute("pageBean", pageBean);
		request.setAttribute("cid", cid);

		// 定义一个记录历史商品信息的集合
		List<Product> historyProductList = new ArrayList<Product>();

		// 获得客户端携带的cookie
		Cookie[] cookies = request.getCookies();
		if (cookies != null) {
			for (Cookie cookie : cookies) {
				if ("pids".equals(cookie.getName())) {
					String pids = cookie.getValue();
					String[] split = pids.split("-");
					for (String pid : split) {
						Product pro = service.findProductByPid(pid);
						historyProductList.add(pro);
					}
				}
			}

		}

		// 将历史记录的集合放到域中
		request.setAttribute("historyProductList", historyProductList);

		request.getRequestDispatcher("/product_list.jsp").forward(request, response);
	}

	// 商品详情页
	public void productInfo(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// 获得要查询的商品的pid
		String pid = request.getParameter("pid");
		String cid = request.getParameter("cid");
		String currentPage = request.getParameter("currentPage");

		ProductService service = new ProductService();
		Product product = service.findProductByPid(pid);

		request.setAttribute("product", product);
		request.setAttribute("cid", cid);
		request.setAttribute("currentPage", currentPage);

		// 获取名为pids的cookie
		String pids = pid;
		Cookie[] cookies = request.getCookies();
		if (cookies != null) {
			for (Cookie cookie : cookies) {
				if ("pids".equals(cookie.getName())) {
					pids = cookie.getValue();// pids:3-1-2
					String[] split = pids.split("-");// pids:[3,1,2]
					List<String> asList = Arrays.asList(split);
					LinkedList<String> list = new LinkedList<String>(asList);
					// 判断集合中是否包含pid
					if (list.contains(pid)) {
						list.remove(pid);
					}
					list.addFirst(pid);

					// 将list转换成字符串
					StringBuffer sb = new StringBuffer();
					for (int i = 0; i < list.size() && i < 7; i++) {
						sb.append(list.get(i));
						sb.append("-");// 3-1-2-
					}
					// 去掉3-1-2-后的-
					pids = sb.substring(0, sb.length() - 1);
				}
			}
		}

		Cookie cookie_pids = new Cookie("pids", pids);
		response.addCookie(cookie_pids);

		request.getRequestDispatcher("/product_info.jsp").forward(request, response);
	}

	// 菜单列表
	public void categoryList(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		ProductService service = new ProductService();

		// 先从缓存中查询categoryList 如果有直接使用 没有在从数据库中查询 存到缓存中
		// 1、获得jedis对象 连接redis数据库
		Jedis jedis = JedisPoolUtils.getJedis();
		String categoryListJson = jedis.get("categoryListJson");
		// 2、判断categoryListJson是否为空
		if (categoryListJson == null) {
			System.out.println("缓存没有数据 查询数据库");
			// 准备分类数据
			List<Category> categoryList = service.findAllCategory();
			// 将List格式转换成json格式
			Gson gson = new Gson();
			categoryListJson = gson.toJson(categoryList);
			// 将分类数据写到缓存中
			jedis.set("categoryListJson", categoryListJson);

		}

		response.setContentType("text/html;charset=UTF-8");
		response.getWriter().write(categoryListJson);

	}

	// 网站首页显示页面
	public void index(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ProductService service = new ProductService();
		// 准备热门商品---List<Product>
		List<Product> hotProdcutList = service.findHotProductList();
		// 准备最新商品---List<Product>
		List<Product> newProdcutList = service.findNewProductList();
		// //获取商品类别
		// List<Category> categoryList = service.findAllCategory();

		request.setAttribute("hotProdcutList", hotProdcutList);
		request.setAttribute("newProdcutList", newProdcutList);
		// request.setAttribute("categoryList", categoryList);

		request.getRequestDispatcher("/index.jsp").forward(request, response);
	}
}