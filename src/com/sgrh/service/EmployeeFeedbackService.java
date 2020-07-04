package com.sgrh.service;

import java.time.LocalDate;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.conf.component.Employee;
import com.conf.component.Roles;
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
	
	public boolean isFeedbackExists(String empcode,LocalDate date) {
		Employee emp = empFeedback.isEmpFeedbackExists(empcode, date);
		boolean feedbackExists = false;
		if(emp != null) {
			feedbackExists = true;
		}
		return feedbackExists;
	}
	
	public Employee startEmployeeFeedback(String empCode, String dept, String designation) {
		Employee emp = empFeedback.createOrGetEmployee(empCode, dept, designation);
		int id = empFeedback.addFeedback(emp);
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
	
	public boolean createUser(String username, String password, String role, String createdBy) {
		User user = new User();
		Roles roles = new Roles();
		roles.setRole(role);
		roles.setCreatedBy(createdBy);
		roles.setActiveRole(true);
		
		user.setUsername(username);
		user.setPassword(password);
		user.getRoleList().add(roles);
		user.setCreatedBy(createdBy);
		user.setActive(true);
		return empFeedback.saveUser(user);
	}
}
