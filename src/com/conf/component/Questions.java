package com.conf.component;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.stereotype.Component;

@Component
@Entity
@Table(name="Questions")
public class Questions {
	@Id
	private int id;
	@Column
	private String question;
	@ElementCollection
	@CollectionTable(name="choices")
	private List<String> choices = new ArrayList<>();
	
	public String getQuestion(){
		return question;
	}
	public void setQuestion(String question) {
		this.question = question;
	}
	public List<String> getChoices() {
		return choices;
	}
	public void setChoices(List<String> choices) {
		this.choices = choices;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
}
