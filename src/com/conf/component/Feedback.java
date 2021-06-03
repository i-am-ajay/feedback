package com.conf.component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import javax.annotation.Generated;
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
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.GenerationTime;
import org.hibernate.mapping.Bag;
import org.springframework.stereotype.Component;

@Component
@Entity
public class Feedback {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	
	@CreationTimestamp
	private LocalDate creationDate;
	
	@Column(name="feedback_date")
	private LocalDate feedbackPeriod;
	
	@Column
	private boolean status;
	
	@ManyToOne(cascade= {CascadeType.REFRESH})
	@JoinColumn(name="emp_id")
	private Employee employee;
	
	@Transient
	private EmployeeChoice currentQuestion;
	@Transient
	private int currentQuestionIndex;
	
	@ElementCollection(fetch=FetchType.EAGER)
	@CollectionTable(name="user_question_mapping")
	@Fetch(FetchMode.SUBSELECT)
	private Map<Integer,EmployeeChoice> choiceList = new HashMap<>();
	
	public LocalDate getCreationDate() {
		return creationDate;
	}
	public void setCreationDate(LocalDate creationDate) {
		this.creationDate = creationDate;
	}
	public Map<Integer,EmployeeChoice> getChoiceList() {
		return choiceList;
	}
	public void setChoiceList(Map<Integer,EmployeeChoice> choiceList){
		this.choiceList = choiceList;
	}
	public int getId(){
		return id;
	}
	public Employee getEmployee() {
		return employee;
	}
	public void setEmployee(Employee employee) {
		this.employee = employee;
	}
	public LocalDate getFeedbackPeriod() {
		return feedbackPeriod;
	}
	public void setFeedbackPeriod(LocalDate feedbackPeriod) {
		this.feedbackPeriod = feedbackPeriod;
	}
	
	public boolean isStatus() {
		return status;
	}
	public void setStatus(boolean status) {
		this.status = status;
	}
	
	
	
	
	/*
	public EmployeeChoice getCurrentQuestion() {
		return this.currentQuestion;
	}
	public void setCurrentQuestion(EmployeeChoice currentQuestion) {
		//this.currentQuestion = this.choiceList.get(currentQuestionIndex);
	}
	public int getCurrentQuestionIndex() {
		return currentQuestionIndex;
	}
	public void setCurrentQuestionIndex(int currentQuestionIndex) {
		this.currentQuestionIndex = currentQuestionIndex;
	}
	*/
}
