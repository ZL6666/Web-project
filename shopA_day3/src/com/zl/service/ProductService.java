package com.zl.service;

import java.sql.SQLException;
import java.util.List;

import com.zl.dao.ProductDao;
import com.zl.domain.Category;
import com.zl.domain.PageBean;
import com.zl.domain.Product;

public class ProductService {
	
	//热门商品
	public List<Product> findHotProductList() {
		ProductDao dao = new ProductDao();
		List<Product> hotProductList = null;
		try {
			hotProductList = dao.findHotProductList();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return hotProductList;
	}

	//最新商品
	public List<Product> findNewProductList() {
		ProductDao dao = new ProductDao();
		List<Product> newProductList = null;
		try {
			newProductList = dao.findNewProductList();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return newProductList;
	}

	//获取所有商品类别
	public List<Category> findAllCategory() {
		ProductDao dao = new ProductDao();
		List<Category> categoryList = null;
		try {
			categoryList = dao.findAllCategory();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return categoryList;
	}

	public PageBean findProductListByCid(String cid,int currentPage,int currentCount) {
		ProductDao dao = new ProductDao();
		PageBean pageBean = new PageBean();
		//封装数据
		//当前页
		
		pageBean.setCurrentPage(currentPage);
		//每页显示条数
		
		pageBean.setCurrentCount(currentCount);
		//总条数
		int totalCount = 0;
		try {
			totalCount = dao.getCount(cid);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		pageBean.setTotalCount(totalCount);
		//总页数
		int totalPage = (int) Math.ceil(1.0*totalCount/currentCount);
		pageBean.setTotalPage(totalPage);
		//当前页的商品数据
		// 当前页与起始索引index的关系
		int index = (currentPage-1)*currentCount;
		List<Product> list = null;
		try {
			list = dao.findProductByPage(cid,index,currentCount);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		pageBean.setList(list);
		

		return pageBean;
	}
	
	//根据pid查询商品
	public Product findProductByPid(String pid) {
		ProductDao dao = new ProductDao();
		Product product = null;
		try {
			product = dao.findProductByPid(pid);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return product;
	}

}
