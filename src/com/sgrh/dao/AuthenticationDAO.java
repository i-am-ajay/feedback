package com.sgrh.dao;

import java.time.LocalDate;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.conf.component.User;

@Repository
public class AuthenticationDAO {
	@Autowired
	@Qualifier(value="feedback_factory")
	SessionFactory factory;
	
	@Transactional("feedback")
	// get user details for databse user table.
	public User getUser(String userName) {
		Session session = factory.getCurrentSession();
		User user = session.get(User.class, userName);
		return user;
	}
	
	@Transactional("feedback")
	public void saveUser(User user) {
		Session session = factory.getCurrentSession();
		user.setCreationDate(LocalDate.now());
		session.saveOrUpdate(user);
		session.flush();
	}
	
	@Transactional("feedback")
	public void updatePassword(User user) {
		Session session = factory.getCurrentSession();
		session.update(user);
		session.flush();
	}
}
