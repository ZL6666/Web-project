package com.zl.dao.impl;

import org.hibernate.Session;

import com.zl.dao.LinkManDao;
import com.zl.domain.LinkMan;
import com.zl.utils.HibernateUtils;

public class LinkManDaoImpl implements LinkManDao {

	//保存联系人
	public void save(LinkMan lm) {
		//获得session
		Session session = HibernateUtils.getCurrentSession();
		session.save(lm);
	}

}
