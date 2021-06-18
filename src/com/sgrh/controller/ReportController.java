package com.sgrh.controller;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpSession;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

import com.conf.component.CurrentFeedbackDate;
import com.conf.component.Feedback;
import com.sgrh.service.EmployeeFeedbackService;
import com.sgrh.service.PISService;
import com.sgrh.service.ReportService;

@Controller
@SessionAttributes()
public class ReportController {
	
	private LocalDate feedbackDate;
	
	@Autowired
	private PISService pisService;
	
	@Autowired
	private MainController controller;

	@Autowired
	private EmployeeFeedbackService eFS;
	
	@Autowired
	private ReportService reportService;
	
	private static Map<String,String> smileyMap = new HashMap<>();
	static {
		smileyMap.put("Very Positive","&#128512" );
		smileyMap.put("Positive", "&#128515");
		smileyMap.put("Neutral", "&#128528");
		smileyMap.put("Negative", "&#128551");
		smileyMap.put("Very Negative", "&#128555");
	}
	
	@RequestMapping("pie_chart")
	public @ResponseBody String generatePieChart(@RequestParam(name="dept")String department,
			@RequestParam(name="date") String date) {
		LocalDate feedbackDuration = null;
		if(date != null && date.length() > 0) {
			feedbackDuration = LocalDate.parse(date,DateTimeFormatter.ofPattern("yyyy-MM-dd"));
		}
		else {
			feedbackDuration = this.feedbackDate;
		}
		Map<String,Long> dataMap = reportService.pieChart(department,feedbackDuration);
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
	public @ResponseBody String summary(@RequestParam(name="dept") String dept,
			@RequestParam(name="date") String date) {
		LocalDate feedbackDuration = null;
		if(date != null && date.length() > 0) {
			feedbackDuration = LocalDate.parse(date,DateTimeFormatter.ofPattern("yyyy-MM-dd"));
		}
		else {
			feedbackDuration = this.feedbackDate;
		}
		long totalCount = reportService.empCount(dept);
		long feedbackCount = reportService.feedbackCount(dept,feedbackDuration);
		//long totalCount = 10000;
		JSONObject obj = new JSONObject();
		obj.put("total", totalCount);
		obj.put("feed", feedbackCount);
		obj.put("no_feed", (totalCount - feedbackCount));
		
		return obj.toString();
	}
	
	@RequestMapping("barchart")
	public @ResponseBody String barchart(@RequestParam(name="dept") String dept,@RequestParam(name="date") String date) {
		LocalDate feedbackDuration = null;
		if(date != null && date.length() > 0) {
			feedbackDuration = LocalDate.parse(date,DateTimeFormatter.ofPattern("yyyy-MM-dd"));
		}
		else {
			feedbackDuration = this.feedbackDate;
		}
		HashMap<String,Integer> empFeedbackMap = reportService.employeewiseData(dept, feedbackDuration);
		List<String> strList = new ArrayList<>();
		String result = "[]";
		if(empFeedbackMap !=null) {
			for(String str: empFeedbackMap.keySet()){
				if((str != null) && (str != " ") && (str !="") && str.length()>0){
					JSONObject obj = new JSONObject();
					obj.put("name", str);
					obj.put("value", empFeedbackMap.get(str));
					strList.add(obj.toString());
				}
			}
			result = Arrays.toString(strList.toArray(new String[strList.size()]));
		}
		return result;
	}
	
	@RequestMapping(value ="graphs",method=RequestMethod.GET)
	public ModelAndView showGraph(Map<String,Object> model,HttpSession session,
			@RequestParam(name="dept", required=false)String dept,
			@RequestParam(name="date", required=false)String date){
		// fetch dept list and add to model attribute.
		List<String> deptList = pisService.getDeptList();
		if(session.getAttribute("username") == null || session.getAttribute("username").toString().length() == 0) {
			return new ModelAndView("login");
		}
		LocalDate feedbackDuration = null;
		// get list of question
		if(date != null && date.length() > 0) {
			feedbackDuration = LocalDate.parse(date,DateTimeFormatter.ofPattern("yyyy-MM-dd"));
		}
		else {
			feedbackDuration = this.feedbackDate;
		}
		List<String> list = pisService.getDeptList();
		Map<String,LocalDate> feedbackTimeline = reportService.getFeedbackTimeLineService();
		// fetch a summary of of user feedback and return a json object. 
		Map<String,Long> map = reportService.pieChart(dept,feedbackDuration);
		JSONObject obj = new JSONObject();
		String jsonString = obj.toString();
		model.put("data",jsonString);
		model.put("deptList",pisService.getDeptList());
		model.put("feedbackTimeLine", reportService.getFeedbackTimeLineService());
		model.put("dept", dept);
		model.put("date", date);
		return new ModelAndView("report");
	}
	
	@RequestMapping(value= "details")
	public ModelAndView detailsFeedbackPage(Map<String,Object> model,HttpSession session) {
		if(session.getAttribute("username") == null || session.getAttribute("username").toString().length() == 0) {
			return new ModelAndView("login");
		}
		List<String> list = pisService.getDeptList();
		
		model.put("deptList",list);
		//empFeedbackDetails("Information Technology");
		return new ModelAndView("detailed_feedback");
	}
	
	@RequestMapping(value = "emp_details")
	public @ResponseBody String empFeedbackDetails(@RequestParam(name="dept") String dept,@RequestParam(name="date") String date,
			HttpSession session) {
		LocalDate feedbackDuration = null;
		// get list of question
		if(date != null && date.length() > 0) {
			feedbackDuration = LocalDate.parse(date,DateTimeFormatter.ofPattern("yyyy-MM-dd"));
		}
		else {
			feedbackDuration = this.feedbackDate;
		}
		Map<String,Integer[]> summaryMap = reportService.feedbackDetailsList(dept, feedbackDuration);
		
		List<JSONObject> listJsonObject = new ArrayList<>();
		
		summaryMap.forEach((k,v) -> {
			JSONObject obj = new JSONObject();
			obj.put(k,v);
			listJsonObject.add(obj);
		});
		return Arrays.toString(listJsonObject.toArray(new JSONObject[listJsonObject.size()]));
	}
	
	@RequestMapping(value = "emplist_with_feedback")
	public ModelAndView empListWithFeedback(Map<String,Object> model, @RequestParam(name="dept", required=false) String dept, 
				@RequestParam(name="date", required=false) String feedbackPeriodString,
				HttpSession session){
		if(session.getAttribute("username") == null || session.getAttribute("username").toString().length() == 0) {
			return new ModelAndView("login");
		}
		LocalDate feedbackDuration = null;
		if(feedbackPeriodString != null && feedbackPeriodString.length() > 0) {
			feedbackDuration = LocalDate.parse(feedbackPeriodString,DateTimeFormatter.ofPattern("yyyy-MM-dd"));
		}
		else {
			feedbackDuration = this.feedbackDate;
		}
		Map<String, String> empMap = reportService.employeeListFeedbackSubmitted(dept, feedbackDuration);
		model.put("emp_list",empMap);
		model.put("smiley_map",ReportController.smileyMap);
		model.put("dept",dept);
		model.put("date",feedbackPeriodString);
		return new ModelAndView("emplist_feedback_details");
	}
	
	@RequestMapping(value="emp_and_feedback", method=RequestMethod.GET)
	public String showEmpFeedback(Model model, HttpSession session, 
			@RequestParam(name="emp_code", required= false) String empCode,
			@RequestParam(name="feedback_date", required=false)String feedbackPeriodString,
			@RequestParam(name="dept", required=false)String dept) {
		if(session.getAttribute("username") == null || session.getAttribute("username").toString().length() == 0) {
			return "login";
		}
		LocalDate feedbackDuration = null;
		if(feedbackPeriodString != null && feedbackPeriodString.length() > 0) {
			feedbackDuration = LocalDate.parse(feedbackPeriodString,DateTimeFormatter.ofPattern("yyyy-MM-dd"));
		}
		else {
			feedbackDuration = this.feedbackDate;
		}
		Feedback feedback = reportService.getFeedbackAndEmployee(empCode, feedbackDuration);
		model.addAttribute("feed_obj", feedback);
		model.addAttribute("dept",dept);
		model.addAttribute("date",feedbackDuration);
		return "emp_feedback";
	}
	
	@PostConstruct
	public void getCurrentDate() {
		CurrentFeedbackDate date = eFS.getCurrentFeedbackDate();
		if(date != null) {
			this.feedbackDate = eFS.getCurrentFeedbackDate().getFeedbackDate();
		}
	}
	
	
}
