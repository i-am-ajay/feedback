package com.sgrh.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sgrh.service.EmployeeFeedbackService;
import com.sgrh.service.PISDetails;
import com.sgrh.service.ReportService;

@Controller
public class ReportController {
	@Autowired
	private PISDetails pisDetails;

	@Autowired
	private EmployeeFeedbackService eFS;
	
	@Autowired
	private ReportService service;
	
	@RequestMapping("pie_chart")
	public @ResponseBody String generatePieChart(@RequestParam(name="dept")String department) {
		Map<String,Long> dataMap = service.pieChart(department);
		List<String> strList = new ArrayList<>();
		for(String str: dataMap.keySet()) {
			if(str != null && str != "") {
				JSONObject obj = new JSONObject();
				obj.put("name", str);
				obj.put("value", dataMap.get(str));
				strList.add(obj.toString());
			}
			
		}
		return Arrays.toString(strList.toArray(new String[strList.size()]));
	}
}
