package com.zl.service;

import java.sql.SQLException;
import java.util.List;

import com.zl.dao.ProductDao;
import com.zl.domain.Product;

public class ProductService {
	
	//查询所有商品
	public List<Product> findAllProduct() throws SQLException {
		
		//传递请求到dao层
		ProductDao dao = new ProductDao();
		List<Product> productList = dao.findAllProduct();
		return productList;
	}
	
//	//商品详情页
//	public Product findProductInfo() {
//		
//		//传递请求到dao层
//		ProductDao dao = new ProductDao();
//		Product product = dao.finProductInfo();
//		return product;
//	}

	

}
