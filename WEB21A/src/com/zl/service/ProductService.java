package com.zl.service;

import java.sql.SQLException;
import java.util.List;

import com.zl.dao.ProductDao;
import com.zl.domain.Product;
import com.zl.vo.PageBean;

public class ProductService {

	public List<Product> findAllProudct() throws SQLException {
		
		ProductDao dao = new ProductDao();
		return dao.findAllProduct();
	}

	//分页操作
	public PageBean findPageBean(int currentPage,int currentCount) throws SQLException{
		ProductDao dao = new ProductDao();
		//开始封装pageBean
		PageBean pageBean = new PageBean();
		//封装当前页
		pageBean.setCurrentPage(currentPage);
		//封装每页显示条数
		pageBean.setCurrentCount(currentCount);
		//封装总条数
		int totalCount = dao.getTotalCount();
		pageBean.setTotalCount(totalCount);
		//封装总页数
		int totalPage = (int) Math.ceil(1.0*totalCount/currentCount);
		pageBean.setTotalPage(totalPage);
		//封装每页显示的商品
		int index = (currentPage-1)*currentCount;
		List<Product> productList = dao.findProductListForPageBean(index,currentCount);
		pageBean.setProductList(productList);
		
		return pageBean;
	}

}
