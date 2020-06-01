package com.sgrh.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.sgrh.dao.ReportDao;

@Service
public class ReportService {
	@Autowired
	ReportDao reportDao;
	
	public Map<String,Long> pieChart(String department){
		List<String[]> summaryList = reportDao.pieChart(department);
		HashMap<String,Long> summaryMap = new HashMap<>();
		if(summaryList != null && summaryList.size()>0) {
			for(Object[] str: summaryList) {
				summaryMap.put((String)str[0], (Long)str[1]);
			}
		}
		return summaryMap;
	}
}
