package com.zl.dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import com.zl.domain.Category;
import com.zl.domain.Product;
import com.zl.utils.DataSourceUtils;
import com.zl.vo.Condition;

public class AdminProductDao {
	
	//查询所有商品
	public List<Product> findAllProduct() throws SQLException {
		//从数据库中获取数据
		QueryRunner qr = new QueryRunner(DataSourceUtils.getDataSource());
		String sql = "select * from product";
		List<Product> productList = qr.query(sql, new BeanListHandler<Product>(Product.class));
		return productList;
	}
	
	//查询商品分类
	public List<Category> findAllCategory() throws SQLException {
		// 从数据库中获取数据
		QueryRunner qr = new QueryRunner(DataSourceUtils.getDataSource());
		String sql = "select * from category";
		List<Category> categoryList = qr.query(sql, new BeanListHandler<Category>(Category.class));
		return categoryList;
	}
	
	//添加商品
	public void addProduct(Product product) throws SQLException {
		QueryRunner qr = new QueryRunner(DataSourceUtils.getDataSource());
		String sql = "insert into product values(?,?,?,?,?,?,?,?,?,?)";
		qr.update(sql, product.getPid(),product.getPname(),product.getMarket_price(),
				product.getShop_price(),product.getPimage(),product.getPdate(),product.getIs_hot(),
				product.getPdesc(),product.getPflag(),product.getCid());
	}
	
	//根据pid查询商品
	public Product findProductByPid(String pid) throws SQLException {
		QueryRunner qr= new QueryRunner(DataSourceUtils.getDataSource());
		String sql = "select * from product where pid=?";
		Product product = qr.query(sql, new BeanHandler<Product>(Product.class),pid);
		return product;
	}
	
	//更新商品
	public void updateProduct(Product product) throws SQLException {
		QueryRunner qr = new QueryRunner(DataSourceUtils.getDataSource());
		String sql = "update product set pname=?,market_price=?,shop_price=?,pimage=?,pdate=?,is_hot=?,pdesc=?,pflag=?,cid=? where pid=?";
		qr.update(sql, product.getPname(),product.getMarket_price(),
				product.getShop_price(),product.getPimage(),product.getPdate(),product.getIs_hot(),
				product.getPdesc(),product.getPflag(),product.getCid(),product.getPid());
	}
	
	//根据pid删除商品
	public void delProductByPid(String pid) throws SQLException {
		QueryRunner qr = new QueryRunner(DataSourceUtils.getDataSource());
		String sql = "delete from product where pid=?";
		qr.update(sql, pid);
	}

	public List<Product> findProductListByCondition(Condition condition) throws SQLException {
		QueryRunner qr = new QueryRunner(DataSourceUtils.getDataSource());
		String sql = "select * from product where 1=1";
		List<String> list = new ArrayList<String>();
		if(condition.getPname()!=null&&!condition.getPname().equals("")){
			sql+=" and pname like ? ";
			list.add("%"+condition.getPname()+"%");
		}
		if(condition.getIsHot()!=null&&!condition.getIsHot().equals("")){
			sql+=" and is_hot=? ";
			list.add(condition.getIsHot().trim());
		}
		if(condition.getCid()!=null&&!condition.getCid().equals("")){
			sql+=" and cid=? ";
			list.add(condition.getCid().trim());
		}
		
		List<Product> productList = qr.query(sql, new BeanListHandler<Product>(Product.class), list.toArray());
		return productList;
	}

}
