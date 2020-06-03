package com.sgrh.controller;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.sgrh.service.EmployeeFeedbackService;
import com.sgrh.service.PISService;
import com.sgrh.service.ReportService;

@Controller
public class ReportController {
	@Autowired
	private PISService pisService;

	@Autowired
	private EmployeeFeedbackService eFS;
	
	@Autowired
	private ReportService service;
	
	@RequestMapping("pie_chart")
	public @ResponseBody String generatePieChart(@RequestParam(name="dept")String department) {
		Map<String,Long> dataMap = service.pieChart(department);
		List<String> strList = new ArrayList<>();
		String result = "[]";
		if(dataMap!=null) {
		for(String str: dataMap.keySet()){
			if((str != null) && (str != " ") && (str !="") && str.length()>0){
				JSONObject obj = new JSONObject();
				obj.put("name", str);
				obj.put("value", dataMap.get(str));
				strList.add(obj.toString());
			}
			result = Arrays.toString(strList.toArray(new String[strList.size()]));
		}}
		return result;
	}
	
	@RequestMapping(value="summary")
	public @ResponseBody String summary(@RequestParam(name="dept") String dept) {
		long totalCount = service.empCount(dept);
		long feedbackCount = service.feedbackCount(dept, LocalDate.of(2020, 6, 1));
		System.out.println(feedbackCount);
		//long totalCount = 10000;
		JSONObject obj = new JSONObject();
		obj.put("total", totalCount);
		obj.put("feed", feedbackCount);
		obj.put("no_feed", (totalCount - feedbackCount));
		
		return obj.toString();
	}
	
	@RequestMapping(value ="graphs",method=RequestMethod.GET)
	public ModelAndView showGraph(Map<String,Object> model){
		// fetch dept list and add to model attribute.
		List<String> list = pisService.getDeptList();
		model.put("deptList",list);
		// fetch a summary of of user feedback and return a json object. 
		Map<String,Long> map = service.pieChart("");
		map.forEach((k,v) ->{
			System.out.println(k+ " -> "+v);
		});
		JSONObject obj = new JSONObject();
		obj.put("Name", 100);
		String jsonString = obj.toString();
		System.out.println(jsonString);
		model.put("data",jsonString);
		return new ModelAndView("report");
	}
}
