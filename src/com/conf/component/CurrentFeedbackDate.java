package com.conf.component;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

// This class holds the date of current feedback, it's duration.
@Entity
@Table(name="current_feedback")
public class CurrentFeedbackDate {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	@Column(name="feedback_date")
	private LocalDate feedbackDate;
	
	@Column(name="end_date")
	private LocalDate feedbackEndDate;
	
	@Column(name="duration")
	private int duration;
	
	@Column(name="isOpen")
	private boolean open;
	
	
	public LocalDate getFeedbackDate() {
		return feedbackDate;
	}
	
	public void setFeedbackDate(LocalDate feedbackDate) {
		this.feedbackDate = feedbackDate;
	}
	
	public int getDuration() {
		return duration;
	}
	
	public void setDuration(int duration) {
		this.duration = duration;
	}
	public boolean isOpen() {
		return open;
	}
	
	public void setOpen(boolean isOpen) {
		open = isOpen;
	}
	
	public int getId() {
		return id;
	}
	
	public LocalDate getFeedbackEndDate() {
		return feedbackEndDate;
	}
	
	public void setFeedbackEndDate(LocalDate feedbackEndDate) {
		this.feedbackEndDate = feedbackEndDate;
	}
}
