package com.sgrh.dao;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.orm.hibernate5.LocalSessionFactoryBuilder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.ajay.others.QuestionBank;
import com.conf.component.Dept;
import com.conf.component.Employee;
import com.conf.component.EmployeeChoice;
import com.conf.component.Feedback;
import com.conf.component.Questions;

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
		else {
			System.out.println("EmployeeFeedback#createOrGetEmployee List of size"+employee.getFeedbackList().size());
		}
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
				//System.out.println(questionBank.get(in).getChoices().get(0));
				session.save(questionBank.get(in));
				session.flush();
			}
		}
	}
	
	@Transactional("feedback")
	private Employee getEmployee(String empCode) {
		//SessionFactory factory = feedbackFactoryBean.getObject();
		SessionFactory factory = feedbackFactoryBean;
		Session session = factory.getCurrentSession();
		CriteriaBuilder builder = session.getCriteriaBuilder();
		CriteriaQuery<Employee> criteriaQuery = builder.createQuery(Employee.class);
		Root<Employee> from = criteriaQuery.from(Employee.class);
		criteriaQuery.where(builder.equal(from.get("empCode"), empCode));
		TypedQuery<Employee>query = session.createQuery(criteriaQuery);
		Employee employee;
		try {
			employee = query.getSingleResult();
		}
		catch(NoResultException ex) {
			employee = null;
		}
		return employee;
	}
	
	@Transactional("feedback")
	public int addFeedback(Employee employee) {
		//System.out.println("Beginning Add Feedback method");
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
}
