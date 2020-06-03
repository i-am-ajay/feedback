package com.sgrh.service;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.sgrh.dao.EmpPISDao;
import com.sgrh.dao.ReportDao;

@Service
public class ReportService {
	@Autowired
	ReportDao reportDao;
	
	@Autowired
	EmpPISDao pisDao;
	
	public Map<String,Long> pieChart(String department){
		List<String[]> summaryList = null;
		if(department != null && department !="" && department !=" " && department.length()>0) {
			summaryList = reportDao.pieChartDataDeptWise(department);
		}
		else {
			summaryList = reportDao.pieChartDataAll();
		}
		Map<String,Long> map = null;
		if(summaryList != null && summaryList.size()>0) {
			map = convertUserInput(summaryList);
		}
		return map;
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
}
