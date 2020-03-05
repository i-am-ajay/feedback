package com.conf.component;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Generated;
import javax.persistence.CascadeType;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Embeddable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.GenerationTime;
import org.springframework.stereotype.Component;

@Component
@Entity
public class Feedback {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	
	@org.hibernate.annotations.Generated(GenerationTime.ALWAYS)
	private LocalDate creationDate;
	
	@ManyToOne(cascade= {CascadeType.PERSIST, CascadeType.REMOVE})
	@JoinColumn(name="emp_id")
	private Employee employee;
	
	@ElementCollection
	@CollectionTable(name="question_map")
	@JoinColumn(name="feedback_id")
	@Column(name="answer")
	private Map<Integer,String> questionMap = new HashMap<>();
	
	public LocalDate getCreationDate() {
		return creationDate;
	}
	public void setCreationDate(LocalDate creationDate) {
		this.creationDate = creationDate;
	}
	public Map<Integer, String> getQuestionMap() {
		return questionMap;
	}
	public void setQuestionMap(Map<Integer, String> questionMap) {
		this.questionMap = questionMap;
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
}
