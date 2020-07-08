package com.sgrh.dao;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.hibernate.Criteria;
import org.hibernate.Filter;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.NativeQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.orm.hibernate5.LocalSessionFactoryBuilder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.ajay.others.QuestionBank;
import com.conf.component.CurrentFeedbackDate;
import com.conf.component.Dept;
import com.conf.component.Employee;
import com.conf.component.EmployeeChoice;
import com.conf.component.Feedback;
import com.conf.component.Questions;
import com.conf.component.User;

@Repository
public class EmployeeFeedback{
	@Autowired
	@Qualifier(value="feedback_factory")
	SessionFactory feedbackFactoryBean;
	
	static int count;
	
	@Transactional("feedback")
	public Employee createOrGetEmployee(String empCode, String dept, String designation) {
		Employee employee = null;
		//SessionFactory factory = feedbackFactoryBean.getObject();
		SessionFactory factory = feedbackFactoryBean;
		Session session = factory.getCurrentSession();
		employee = session.find(Employee.class,empCode);
		if(employee == null) {
			employee = new Employee();
			employee.setEmpCode(empCode);
			employee.setDesignation(designation);
			employee.setDepartment(dept);
			session.persist(employee);
		}
		return employee;
	}
	
	@Transactional("feedback")
	public Employee isEmpFeedbackExists(String emp, LocalDate date) {
		// Metod will return employee if feedback exists for given date else null.
		// A filter is used for feedback date in Employee class.
		Session session = feedbackFactoryBean.getCurrentSession();
		session.enableFilter("feedback_datewise").setParameter("feedback_date", date);
		Employee employee = session.get(Employee.class, emp);
		session.disableFilter("feedback_datewise");
		return employee;
	}
	
	@Transactional("feedback")
	public void saveQuestionMapInDB() {
		EmployeeFeedback.count=1;
		if(EmployeeFeedback.count > 0) {
			return;
		}
		else {
			count++;
			//SessionFactory factory = feedbackFactoryBean.getObject();
			SessionFactory factory = feedbackFactoryBean;
			Session session = factory.getCurrentSession();
			Map<Integer,Questions> questionBank = QuestionBank.getInstance().getQuestionMap();
			for(Integer in: questionBank.keySet()) {
				session.save(questionBank.get(in));
				session.flush();
			}
		}
	}
	
	@Transactional("feedback")
	private Employee getEmployee(String empCode,LocalDate date) {
		//SessionFactory factory = feedbackFactoryBean.getObject();
		SessionFactory factory = feedbackFactoryBean;
		Session session = factory.getCurrentSession();
		CriteriaBuilder builder = session.getCriteriaBuilder();
		CriteriaQuery<Employee> criteriaQuery = builder.createQuery(Employee.class);
		Root<Employee> from = criteriaQuery.from(Employee.class);
		criteriaQuery.where(builder.equal(from.get("empCode"), empCode));
		TypedQuery<Employee>query = session.createQuery(criteriaQuery);
		Filter filter = session.enableFilter("feedback_datewise");
		filter.setParameter("feedback_date", date);
		Employee employee;
		try {
			employee = query.getSingleResult();
		}
		catch(NoResultException ex) {
			employee = null;
		}
		session.disableFilter("feedback_datewise");
		return employee;
	}
	
	@Transactional("feedback")
	public int addFeedback(Employee employee) {
		Feedback feedback = new Feedback();
		feedback.setFeedbackPeriod(LocalDate.now().withDayOfMonth(1));
		for(Integer i: QuestionBank.getInstance().getQuestionMap().keySet()){
			feedback.getChoiceList().put(i,new EmployeeChoice(i, ""));
		}
		
		//SessionFactory factory = feedbackFactoryBean.getObject();
		SessionFactory factory = feedbackFactoryBean;
		Session session = factory.getCurrentSession();
		employee.getFeedbackList().add(feedback);
		feedback.setEmployee(employee);
		session.persist(feedback);
		session.flush();
		return feedback.getId();
	}
	
	@Transactional("feedback")
	public void saveEmpFeedback(Employee emp){
		//SessionFactory factory = feedbackFactoryBean.getObject();
		SessionFactory factory = feedbackFactoryBean;
		Session session = factory.getCurrentSession();
		//Employee emp1 = session.get(Employee.class, emp.getEmpCode());
		//session.merge(emp.getFeedbackList().get(0));
		session.saveOrUpdate(emp);
		session.flush();
	}
	
	@Transactional("feedback")
	public void getDept() {
		//SessionFactory factory = feedbackFactoryBean.getObject();
		SessionFactory factory = feedbackFactoryBean;
		Session session = factory.getCurrentSession();
		CriteriaBuilder builder = session.getCriteriaBuilder();
		CriteriaQuery<Dept> criteria = builder.createQuery(Dept.class);
		Root<Dept> root = criteria.from(Dept.class);
		criteria.multiselect(root.get(""));
	}
	
	@Transactional 
	public void getDesignation() {
		
	}
	
	@Transactional("feedback")
	public void updateEmpFeedback(Employee emp){
		//SessionFactory factory = feedbackFactoryBean.getObject();
		SessionFactory factory = feedbackFactoryBean;
		Session session = factory.getCurrentSession();
		Feedback feedback = emp.getFeedbackList().get(0);
		feedback = session.get(Feedback.class,emp.getCurrentFeedbackId());
		feedback.setChoiceList(emp.getFeedbackList().get(0).getChoiceList());
		session.flush();
	}
	
	@Transactional("feedback")
	public void addQuestions(){
		//SessionFactory factory = feedbackFactoryBean.getObject();
		SessionFactory factory = feedbackFactoryBean;
		Session session = factory.getCurrentSession();
		Questions question = new Questions();
		question.setQuestion("Do you know your rights");
		question.getChoices().add("Y");
		question.getChoices().add("N");
		question.getChoices().add("DN");
		
		Questions question1 = new Questions();
		question1.setQuestion("Do you know leaves are not your right?");
		question1.getChoices().add("Y");
		question1.getChoices().add("N");
		question1.getChoices().add("DN");
		session.persist(question);
		session.persist(question1);
	}
	
	@Transactional("feedback")
	// get user details for databse user table.
	public User getUser(String userName) {
		Session session = feedbackFactoryBean.getCurrentSession();
		User user = session.get(User.class, userName);
		return user;
	}
	
	@Transactional("feedback")
	public boolean saveUser(User user) {
		User tempUser = getUser(user.getUsername());
		boolean isUserCreated = false;
		if(tempUser == null) {
			Session session = feedbackFactoryBean.getCurrentSession();
			session.save(user);
			session.flush();
			isUserCreated = true;
		}
		return isUserCreated;
	}
	
	@Transactional("feedback")
	public boolean setCurrentFeedbackDate(LocalDate startDate, int duration) {
		LocalDate endDate = startDate.plusMonths(duration);
		Session session = feedbackFactoryBean.getCurrentSession();
		String queryString = "FROM CurrentFeedbackDate WHERE feedbackEndDate > :startDate AND open=true ";
		TypedQuery<CurrentFeedbackDate> selectionQuery = session.createQuery(queryString,CurrentFeedbackDate.class); 
		selectionQuery.setParameter("startDate", startDate);
		List<CurrentFeedbackDate> feedbackDateList = selectionQuery.getResultList();
		if(feedbackDateList.size() > 0) {
			return false;
		}
		String updateString = "UPDATE CurrentFeedbackDate SET isOpen = 0 WHERE isOpen = 1";
		Query updateQuery = session.createQuery(updateString);
		updateQuery.executeUpdate();
		session.flush();
	    System.out.println("Update Done");
		String insertString = "INSERT INTO current_feedback (feedback_date, duration, end_date,isopen ) VALUES (?, ?, ?,true)";
		NativeQuery insertQuery = session.createNativeQuery(insertString);
		insertQuery.setParameter(1, startDate);
		insertQuery.setParameter(2, duration);
		insertQuery.setParameter(3, endDate);
		System.out.println("Before Update");
		int val = insertQuery.executeUpdate();
		if(val > 0) {
			return true;
		}
		System.out.println("After update");
		return false;
	}
	
	@Transactional("feedback")
	public CurrentFeedbackDate getCurrentFeedbackDate(){
		Session session = feedbackFactoryBean.getCurrentSession();
		String queryString = "FROM CurrentFeedbackDate WHERE open=true";
		TypedQuery<CurrentFeedbackDate> selectionQuery = session.createQuery(queryString, CurrentFeedbackDate.class);
		CurrentFeedbackDate obj = null;
		try {
			obj = selectionQuery.getSingleResult();
		}
		catch(RuntimeException ex) {
			obj = null;
		}
		return obj;
	}
}
