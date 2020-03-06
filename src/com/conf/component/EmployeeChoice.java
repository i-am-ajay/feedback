package com.conf.component;

import javax.persistence.Embeddable;

@Embeddable
public class EmployeeChoice {
	private String answer;
	private int questionid;
	
	public EmployeeChoice() {
		
	}
	public EmployeeChoice(int questionid, String answer) {
		this.questionid = questionid;
		this.answer = answer;
	}
	public String getAnswer() {
		return answer;
	}
	public void setAnswer(String answer) {
		this.answer = answer;
	}
	public int getQuestionid() {
		return questionid;
	}
	public void setQuestionid(int questionid) {
		this.questionid = questionid;
	}
	
	
}
