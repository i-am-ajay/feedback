package com.sgrh.service;

import java.math.BigInteger;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.ajay.others.QuestionBank;
import com.conf.component.Employee;
import com.conf.component.Feedback;
import com.sgrh.dao.EmpPISDao;
import com.sgrh.dao.ReportDao;

@Service
public class ReportService {
	@Autowired
	ReportDao reportDao;
	
	@Autowired
	EmpPISDao pisDao;
	// Department PIE chart
	public Map<String,Long> pieChart(String department, LocalDate feedbackDate){
		List<String[]> summaryList = null;
		if(department != null && department !="" && department !=" " && department.length()>0) {
			summaryList = reportDao.pieChartDataDeptWise(department,feedbackDate);
		}
		else {
			summaryList = reportDao.pieChartDataAll(feedbackDate);
		}
		Map<String,Long> map = null;
		if(summaryList != null && summaryList.size()>0) {
			map = convertUserInput(summaryList);
		}
		return map;
	}
	
	// employee bar chart.
	public HashMap<String,Integer> employeewiseData(String department,LocalDate date) {
		List<Object[]> empFeedbackList = reportDao.empList(department, date);
		BigInteger integer = (BigInteger)reportDao.getQuestionCount();
		int finalCount = integer.intValue();
		int size = empFeedbackList.size();
		HashMap<String,Integer> feedbackMap = new LinkedHashMap<>();
		feedbackMap.put("Very Positive",0);
		feedbackMap.put("Positive", 0);
		feedbackMap.put("Neutral", 0);
		feedbackMap.put("Negative", 0);
		feedbackMap.put("Very Negative", 0);
		
		for(Object[] objArray : empFeedbackList) {
			feedbackAnalysis(feedbackMap, objArray, finalCount);
		}
		
		feedbackMap.forEach((k,v) -> {
			feedbackMap.put(k, (int)(((float)v/(float)size)*100));
		});
		return feedbackMap;
	}
	
	// employee list who submitted feedback
	public HashMap<String,String> employeeListFeedbackSubmitted(String department,LocalDate date) {
		List<Object[]> empFeedbackList = reportDao.empList(department, date);
		BigInteger integer = (BigInteger)reportDao.getQuestionCount();
		int finalCount = integer.intValue();
		int size = empFeedbackList.size();
		HashMap<String,String> employeeMap = new HashMap<>();
		
		for(Object[] objArray : empFeedbackList) {
			employeeMap.put((String)objArray[0],feedbackAnalysisEmployee(objArray, finalCount));
		}
		return employeeMap;
	}
	
	
	private void feedbackAnalysis(HashMap<String,Integer> map, Object[] obj, int finalCount) {
		BigInteger positiveCount = (BigInteger)obj[1];
		BigInteger neutralCount = (BigInteger)obj[2];
		BigInteger negativeCount = (BigInteger)obj[3];
		
		float positivePercentage = ((float)positiveCount.intValue() / (float)finalCount) * 100;
		float neutralPercentage  =((float)neutralCount.intValue() / (float)finalCount) * 100;
		float negativePercentage = ((float)negativeCount.intValue() / (float) finalCount) * 100;
		
		if(positivePercentage >= 80 )
			map.put("Very Positive", map.get("Very Positive")+1);
		else if(positivePercentage >=60 && positivePercentage<80)
			map.put("Positive", map.get("Positive")+1);
		else if(negativePercentage >= 80)
			map.put("Very Negative", map.get("Very Negative")+1);
		else if(negativePercentage >= 60 && negativePercentage < 80)
			map.put("Negative",map.get("Negative")+1);
		else if((positivePercentage >=40 && positivePercentage <60) || (negativePercentage >= 40 && negativePercentage < 60))
			map.put("Neutral", map.get("Neutral")+1);
		else if(neutralPercentage > 33)
			map.put("Neutral", map.get("Neutral")+1);
	}
	
	// Feedback categorization for single employee
	private String feedbackAnalysisEmployee(Object[] obj, int finalCount) {
		String feedback = null;
		BigInteger positiveCount = (BigInteger)obj[1];
		BigInteger neutralCount = (BigInteger)obj[2];
		BigInteger negativeCount = (BigInteger)obj[3];
		
		float positivePercentage = ((float)positiveCount.intValue() / (float)finalCount) * 100;
		float neutralPercentage  =((float)neutralCount.intValue() / (float)finalCount) * 100;
		float negativePercentage = ((float)negativeCount.intValue() / (float) finalCount) * 100;
		
		if(positivePercentage >= 80 )
			feedback = "Very Positive";
		else if(positivePercentage >=60 && positivePercentage<80)
			feedback = "Positive";
		else if(negativePercentage >= 80)
			feedback = "Very Negative";
		else if(negativePercentage >= 60 && negativePercentage < 80)
			feedback = "Negative";
		else if((positivePercentage >=40 && positivePercentage <60) || (negativePercentage >= 40 && negativePercentage < 60))
			feedback = "Neutral";
		else if(neutralPercentage > 33)
			feedback = "Neutral";
		
		return feedback;
	}
	
	public Map<String,Long> convertUserInput(List<String[]> summaryList){
		List<Object[]> list = reportDao.getCategory();
		
		HashMap<String,Long> summaryMap = new HashMap<>();
		HashMap<String,String> categoryMap = new HashMap<>();
		// Convert list of values and category to map.
		for(Object[] obj : list) {
			categoryMap.put((String)obj[0],(String)obj[1]);
		}
		for(Object[] str: summaryList) {
			String key = categoryMap.get((String)str[0]);
			summaryMap.put(key, summaryMap.getOrDefault(key, 0L)+(Long)str[1]);
		}
		return summaryMap;
	}
	
	public long empCount(String dept) {
		return pisDao.empCount(dept);
	}
	
	public long feedbackCount(String dept,LocalDate date) {
		return reportDao.getfeedbackCount(dept, date);
	}
	
	public Map<String,Integer[]> feedbackDetailsList(String dept,LocalDate date){
		List<Object[]> feedbackList = reportDao.feedbackDetails(dept, date);
		// From feedbackList create a map question as keys and count array as values.
		Map<String,Integer[]> questionFeedbackSummary = new HashMap<>();
		for(Object[] obj : feedbackList) {
			String question = QuestionBank.getInstance().getQuestionStatement((int)obj[0]);
			/* summary Array will hold user count for each question, how many gave 
			 * positive/ negative / neutral reply for a question.
			 * index 0 has positive count
			 * index 1 has neutral count
			 * index 2 has negative count
			 * */
			questionFeedbackSummary.put(question, questionFeedbackSummary.getOrDefault(question, new Integer[] {0,0,0}));
			Integer[] summaryArray = questionFeedbackSummary.get(question);
			if(obj[2].equals("Positive")) {
				BigInteger integer = (BigInteger) obj[1];
				if(summaryArray[0] == null || summaryArray[0] == 0) {
					summaryArray[0] = integer.intValue();
				}
				else {
					summaryArray[0] += integer.intValue();
				}
			}
			else if(obj[2].equals("Negative")) {
				if(summaryArray[2] == null || summaryArray[2] == 0) {
					BigInteger integer = (BigInteger)obj[1];
					summaryArray[2] = integer.intValue();
				}
				else {
					BigInteger integer = (BigInteger)obj[1];
					summaryArray[2] += integer.intValue();
				}
			}
			else if(obj[2].equals("Neutral")) {
				if(summaryArray[1] == null || summaryArray[1] == 0) {
					BigInteger integer = (BigInteger)obj[1];
					summaryArray[1] = integer.intValue();
				}
				else {
					BigInteger integer = (BigInteger)obj[1];
					summaryArray[1] += integer.intValue();
				}
			}
			else {}
		}
		
		
		return questionFeedbackSummary;
	}
	
	public Map<String,LocalDate> getFeedbackTimeLineService(){
		List<LocalDate> listLocalDate = reportDao.getFeedbackTimeLine();
		Map<String,LocalDate> mapDate = new LinkedHashMap<>();
		for(LocalDate date : listLocalDate) {
			String strDate = date.getMonth().name()+"-" + Integer.toString(date.getYear());
			mapDate.put(strDate, date);
		}
		return mapDate;
	}
	
	public Feedback getFeedbackAndEmployee(String empCode, LocalDate feedbackDate) {
		Feedback feedback = null;
		List<Feedback> feedbackList = reportDao.getFeedbackandEmployee(empCode, feedbackDate);
		if(feedbackList != null && feedbackList.size() > 0) {
			feedback = feedbackList.get(0);
		}
		return feedback;
	}
}
