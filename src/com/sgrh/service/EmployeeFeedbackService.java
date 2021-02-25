package com.sgrh.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.conf.component.CurrentFeedbackDate;
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
		List<Employee> empList = empFeedback.isEmpFeedbackExists(empcode, date);
		Employee emp = null;
		if(empList != null && empList.size() > 0) {
			emp = empList.get(0);
		}
		boolean feedbackExists = false;
		if(emp != null) {
			feedbackExists = true;
		}
		return feedbackExists;
	}
	
	public Employee startEmployeeFeedback(String empCode, String dept, String designation, LocalDate feedbackDate) {
		Employee emp = empFeedback.createOrGetEmployee(empCode, dept, designation);
		int id = empFeedback.addFeedback(emp, feedbackDate);
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
	
	public boolean saveCurrentDate(LocalDate date, int duration) {
		return empFeedback.setCurrentFeedbackDate(date, duration);
	}
	
	public CurrentFeedbackDate getCurrentFeedbackDate() {
		return empFeedback.getCurrentFeedbackDate();
	}
}
