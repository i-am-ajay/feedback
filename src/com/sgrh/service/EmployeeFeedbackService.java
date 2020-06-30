package com.sgrh.service;

import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.conf.component.Employee;
import com.conf.component.User;
import com.sgrh.dao.EmployeeFeedback;

@Service
public class EmployeeFeedbackService {
	@Autowired
	private EmployeeFeedback empFeedback;
	
	int count;
	
	public void generatedQuestions() {
		empFeedback.saveQuestionMapInDB();
		//empFeedback.addQuestions();
	}
	
	public boolean isFeedbackExists(String empcode) {
		Employee emp = empFeedback.isEmpFeedbackExists(empcode);
		boolean feedbackExists = false;
		if(emp != null) {
			feedbackExists = true;
		}
		return feedbackExists;
	}
	
	public Employee startEmployeeFeedback(String empCode, String dept, String designation) {
		Employee emp = empFeedback.createOrGetEmployee(empCode, dept, designation);
		int id = empFeedback.addFeedback(emp);
		System.out.println("EmployeeFeedbackService#startEmployeeFeedback Feedback Size"+emp.getFeedbackList().size());
		emp.setCurrentFeedbackId(id);
		return emp;
	}
	
	public void saveFeedback(Employee emp) {
		empFeedback.saveEmpFeedback(emp);
	}
	
	public void updateFeeback(Employee emp) {
		empFeedback.updateEmpFeedback(emp);
	}
	
	public User authenticateUser(String emp, String password) {
		User user = empFeedback.getUser(emp);
		if(user != null) {
			return user;
		}
		else {
			return null;
		}
	}
}
