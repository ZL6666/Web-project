package com.zl.dao;

import java.sql.SQLException;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import com.zl.domain.Product;
import com.zl.utils.DataSourceUtils;

public class ProductDao {

	public List<Product> findAllProduct() throws SQLException {
		
		//从数据库中获取数据
		QueryRunner qr = new QueryRunner(DataSourceUtils.getDataSource());
		String sql = "select * from product";
		List<Product> productList = qr.query(sql, new BeanListHandler<Product>(Product.class));
		return productList;
	}

//	public Product finProductInfo() {
//		//从数据库中获取数据
//		QueryRunner qr = new QueryRunner(DataSourceUtils.getDataSource());
//		String sql = "select * from product where pname=?";
//		qr.query(sql, new BeanHandler<Product>(Product.class), ${productList.pname});
//		return null;
//	}

}
