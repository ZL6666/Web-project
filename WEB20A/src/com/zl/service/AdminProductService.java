package com.zl.service;

import java.sql.SQLException;
import java.util.List;

import com.zl.dao.AdminProductDao;
import com.zl.domain.Category;
import com.zl.domain.Product;

public class AdminProductService {

	//查询所有商品
	public List<Product> findAllProduct() throws SQLException {
		//传递请求到dao层
		AdminProductDao dao = new AdminProductDao();
		return dao.findAllProduct();
	}

	//查询商品分类
	public List<Category> findAllCategory() throws SQLException {
		//传递请求到dao层
		AdminProductDao dao = new AdminProductDao();
		return dao.findAllCategory();
	}

	//添加商品
	public void addProduct(Product product) throws SQLException {
		AdminProductDao dao = new AdminProductDao();
		dao.addProduct(product);
	}
	
	//根据pid查询商品
	public Product finProductByPid(String pid) throws SQLException {
		AdminProductDao dao = new AdminProductDao();
		return dao.findProductByPid(pid);
	}
	
	//更新商品
	public void updateProduct(Product product) throws SQLException {
		AdminProductDao dao = new AdminProductDao();
		dao.updateProduct(product);
	}
	
	//根据pid删除商品
	public void delProductByPid(String pid) throws SQLException {
		AdminProductDao dao = new AdminProductDao();
		dao.delProductByPid(pid);
	}

}
