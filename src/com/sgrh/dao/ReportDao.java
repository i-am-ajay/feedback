package com.sgrh.dao;

import java.util.List;
import java.util.Map;

import javax.persistence.Query;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.NativeQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class ReportDao {
	@Autowired
	@Qualifier(value="feedback_factory")
	SessionFactory sessionFactory;
	
	@Transactional("feedback")
	public List<String[]> pieChartDataDeptWise(String department) {
		Session session = sessionFactory.getCurrentSession();
		Query summaryData = session.createQuery("select c.answer, count(c.answer)from Employee e join e.feedbackList f join f.choiceList c"
				+ " where e.department = :dept group by c.answer");
		summaryData.setParameter("dept", department);
		List<String[]> l = summaryData.getResultList();
		return l;
	}
	
	@Transactional("feedback")
	public List<String[]> pieChartDataAll() {
		Session session = sessionFactory.getCurrentSession();
		Query summaryData = session.createQuery("select c.answer, count(c.answer)from Employee e join e.feedbackList f join f.choiceList c"
				+ " group by c.answer");
		List<String[]> l = summaryData.getResultList();
		return l;
	}
	
	@Transactional("feedback")
	public List<Object[]> getCategory() {
		//SessionFactory factory = feedbackFactoryBean;
		Session session = sessionFactory.getCurrentSession();
		NativeQuery<Object[]> query= session.createNativeQuery("SELECT answer,category FROM answer_cat");
		return query.getResultList();
	}
}
