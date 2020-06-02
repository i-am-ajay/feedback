package com.sgrh.controller;

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
		for(String str: dataMap.keySet()) {
			if((str != null) && (str != " ") && (str !="") && str.length()>0) {
				JSONObject obj = new JSONObject();
				obj.put("name", str);
				obj.put("value", dataMap.get(str));
				strList.add(obj.toString());
			}
			
		}
		return Arrays.toString(strList.toArray(new String[strList.size()]));
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
