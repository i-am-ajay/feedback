package com.sgrh.dao;

import java.util.List;
import java.util.Map;

import javax.persistence.Query;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
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
	public List<String[]> pieChart(String department) {
		department = "Information Technology";
		Session session = sessionFactory.getCurrentSession();
		Query summaryData = session.createQuery("select c.answer, count(c.answer)from Employee e join e.feedbackList f join f.choiceList c"
				+ " where e.department = :dept group by c.answer");
		summaryData.setParameter("dept", department);
		List<String[]> l = summaryData.getResultList();
		return l;
	}
}
