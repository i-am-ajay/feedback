package com.sgrh.service;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.conf.component.Employee;
import com.sgrh.dao.EmployeeFeedback;

@Service
public class EmployeeFeedbackService {
	@Autowired
	private EmployeeFeedback empFeedback;
	
	public void generatedQuestions() {
		empFeedback.saveQuestionMapInDB();
		//empFeedback.addQuestions();
	}
	
	public Employee startEmployeeFeedback(String empCode, String dept, String designation) {
		Employee emp = empFeedback.createOrGetEmployee(empCode, dept, designation);
		empFeedback.addFeedback(emp);
		return emp;
	}
	
	public void saveFeedback(Employee emp) {
		empFeedback.saveEmpFeedback(emp);
	}
}
