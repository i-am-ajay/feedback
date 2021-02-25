package com.sgrh.dao;

import java.math.BigInteger;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.NativeQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.conf.component.Employee;

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
	
	@Transactional("feedback")
	public int getfeedbackCount(String dept,LocalDate date) {
		Session session = sessionFactory.getCurrentSession();
		NativeQuery query =null;
		if(dept != null && dept.length()>0) {
			NativeQuery empCodeQuery = session.createNativeQuery("SELECT empcode FROM employee where 1=1 AND department = :dept");
			empCodeQuery.setParameter("dept", dept);
			query = session.createNativeQuery("SELECT count(DISTINCT emp_id) FROM feedback WHERE 1=1 AND "
					+ " feedback_Date = :date AND emp_id IN (:emplist)");
			query.setParameter("date", date);
			query.setParameter("emplist", empCodeQuery.getResultList());
		}
		else {
			query = session.createNativeQuery("SELECT count(DISTINCT emp_id) FROM feedback WHERE 1=1 AND "
					+ " feedback_Date = :date");
			query.setParameter("date", date);
			}
		Object obj = query.getSingleResult();
		BigInteger i = (BigInteger)obj;
		return i.intValue();
	}
	
	/**
	 * Calls a stored procedure that return a user with count of positive, negative and neutral answers for a 
	 * particular feedback.
	 * @param department
	 * @param date
	 * @return
	 */
	@Transactional("feedback")
	public List<Object[]> empList(String department, LocalDate date){
		Session session = sessionFactory.getCurrentSession();
		if(department == null || department.length() == 0) {
			department = null;
		}
		NativeQuery query = session.createNativeQuery("CALL user_feedback_summary(:sdate, :dept)");
		query.setParameter("dept", department);
		query.setParameter("sdate", date);
		/*if(department != null && department.length() > 0){
			query.setParameter("dept", department);
			query.setParameter("date", date);
		}
		else {
			query.setParameter("date", date);
		}*/
		return query.getResultList();
	}
	
	@Transactional("feedback")
	public Object getQuestionCount() {
		Session session = sessionFactory.getCurrentSession();
		NativeQuery query = session.createNativeQuery("SELECT count(*) FROM questions");
		return query.getSingleResult();
	}
	
	private String getQuery(String dept,LocalDate date) {
		String query = "";
		if(dept != null && dept.length() > 0) {
			query = "SELECT Summary.empcode, Summary.category, max(w) FROM(\r\n" + 
					"	SELECT employee.empcode, answer_cat.category, count(answer_cat.category) as w FROM employee INNER JOIN feedback ON feedback.emp_id = employee.EmpCode INNER JOIN \r\n" + 
					"	user_question_mapping ON feedback.id = user_question_mapping.Feedback_id INNER JOIN answer_cat\r\n" + 
					"	ON user_question_mapping.answer = answer_cat.answer\r\n" + 
					"	WHERE 1=1\r\n" + 
					"   	AND employee.department = :dept AND feedback.feedback_date = :date" +
					"	GROUP BY employee.EmpCode, answer_cat.category ORDER BY empcode,w desc\r\n" + 
					"	) AS Summary GROUP BY Summary.empcode";
		}
		else {
			query =  "SELECT Summary.empcode, Summary.category, max(w) FROM(\r\n" + 
					"	SELECT employee.empcode, answer_cat.category, count(answer_cat.category) as w FROM employee INNER JOIN feedback ON feedback.emp_id = employee.EmpCode INNER JOIN \r\n" + 
					"	user_question_mapping ON feedback.id = user_question_mapping.Feedback_id INNER JOIN answer_cat\r\n" + 
					"	ON user_question_mapping.answer = answer_cat.answer\r\n" + 
					"	WHERE 1=1\r\n" + 
					"   	AND feedback.feedback_date = :date" +
					"	GROUP BY employee.EmpCode, answer_cat.category ORDER BY empcode,w desc\r\n" + 
					"	) AS Summary GROUP BY Summary.empcode";
		}
		return query;
	}
	
	// Find count of positive, negative and neutral answer for each question.
	@Transactional("feedback")
	public List<Object[]> feedbackDetails(String dept, LocalDate date){
		Session session = sessionFactory.getCurrentSession();
		NativeQuery query = session.createNativeQuery(feedbackDetailsQuery(dept));
		if ((dept != null) && (dept.length() > 0)) {
			query.setParameter("date", date);
			query.setParameter("dept", dept);
		}
		else {
			query.setParameter("date", date);
		}
		return query.getResultList();
	}
	
	private String feedbackDetailsQuery(String dept) {
		String query = null;
		if(dept != null && dept.length() > 0) {
			query = "SELECT questionid, count(category), category FROM user_question_mapping JOIN"
					+ " answer_cat ON user_question_mapping.answer = answer_cat.answer WHERE 1=1 AND "
					+ "feedback_id IN (SELECT ID FROM feedback WHERE 1=1 AND feedback_date = :date AND emp_id IN (" + 
					"		SELECT employee.EmpCode FROM employee WHERE employee.department = :dept))" + 
					" GROUP BY questionid, category";
		}
		else {
			query = "SELECT questionid, count(category), category FROM user_question_mapping JOIN "
					+ " answer_cat ON user_question_mapping.answer = answer_cat.answer WHERE 1=1 "
					+ "AND feedback_id IN (	SELECT ID FROM feedback WHERE 1=1 AND "
					+"feedback_date = :date ) GROUP BY questionid, category";
		}
		return query;
	}
}
