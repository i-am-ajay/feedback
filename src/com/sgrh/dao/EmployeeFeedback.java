package com.sgrh.dao;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.orm.hibernate5.LocalSessionFactoryBuilder;
import org.springframework.stereotype.Repository;

import com.ajay.others.QuestionBank;
import com.conf.component.Employee;
import com.conf.component.Feedback;
import com.conf.component.Questions;

@Repository
public class EmployeeFeedback {
	@Autowired
	LocalSessionFactoryBean factoryBean;
	
	
	@Transactional
	public Employee createOrGetEmployee(String empCode, String dept, String designation) {
		Employee employee = null;
		SessionFactory factory = factoryBean.getObject();
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
	
	@Transactional
	public void saveQuestionMapInDB() {
		SessionFactory factory = factoryBean.getObject();
		Session session = factory.getCurrentSession();
		Map<Integer,Questions> questionBank = QuestionBank.getInstance().getQuestionMap();
		for(Integer in: questionBank.keySet()) {
			System.out.println(questionBank.get(in).getChoices().get(0));
			session.save(questionBank.get(in));
			session.flush();
		}
		//session.flush();
	}
	
	private Employee getEmployee(String empCode) {
		SessionFactory factory = factoryBean.getObject();
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
	
	@Transactional
	public void addFeedback(Employee employee) {
		Feedback feedback = new Feedback();
		feedback.setCreationDate(LocalDate.now());
		for(Integer i: QuestionBank.getInstance().getQuestionMap().keySet()) {
			feedback.getQuestionMap().put(i, "");
		}
		SessionFactory factory = factoryBean.getObject();
		Session session = factory.getCurrentSession();
		employee.getFeedbackList().add(feedback);
		feedback.setEmployee(employee);
		session.save(feedback);
	}
	
	@Transactional
	public void saveEmpFeedback(Employee emp){
		SessionFactory factory = factoryBean.getObject();
		Session session = factory.getCurrentSession();
		session.saveOrUpdate(emp);
		session.flush();
	}
	
	@Transactional
	public void addQuestions() {
		SessionFactory factory = factoryBean.getObject();
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
