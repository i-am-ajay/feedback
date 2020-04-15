package com.conf.component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Embeddable;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Transient;

import org.springframework.stereotype.Component;

@Component
@Entity
public class Employee {
	@Id
	@Column
	private String EmpCode;
	@Column
	private String designation;
	@Column
	private String department;
	
	@Transient
	private int currentFeedbackId;
	
	@OneToMany(mappedBy="employee", cascade= {CascadeType.PERSIST, CascadeType.REMOVE},fetch=FetchType.EAGER)
	List<Feedback> feedbackList = new ArrayList<>();
	
	public String getEmpCode() {
		return EmpCode;
	}
	public void setEmpCode(String empCode) {
		EmpCode = empCode;
	}
	public String getDesignation() {
		return designation;
	}
	public void setDesignation(String designation) {
		this.designation = designation;
	}
	public String getDepartment() {
		return department;
	}
	public void setDepartment(String department) {
		this.department = department;
	}
	public List<Feedback> getFeedbackList() {
		return feedbackList;
	}
	public void setFeedbackList(List<Feedback> list) {
		this.feedbackList = list;
	}
	
	public EmployeeChoice getQuestionChoice(int feedback, int questionId) {
		return null;//feedbackList.get(feedback).getChoiceList().get(questionId);
	}
	public int getCurrentFeedbackId() {
		return currentFeedbackId;
	}
	public void setCurrentFeedbackId(int currentFeedbackId) {
		this.currentFeedbackId = currentFeedbackId;
	}
	
	
	
}
