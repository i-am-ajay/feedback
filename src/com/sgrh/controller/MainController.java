package com.sgrh.controller;

import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;


import javax.annotation.PostConstruct;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.conf.component.CurrentFeedbackDate;
import com.conf.component.Employee;
import com.sgrh.service.EmployeeFeedbackService;
import com.sgrh.service.PISService;
import com.sgrh.service.ReportService;

@Controller
@SessionAttributes({"emp"})
public class MainController{
	
	private LocalDate feedbackDate;
	int duration;
	@Autowired
	EmployeeFeedbackService eFS;
	@Autowired
	ReportService service;
	
	@Autowired
	PISService pisService;
	
	Employee empGlobal;
	
	int feedbackId;
	
	Employee emp;
	private LocalDate feedbackEndDate;
	
	// User authentication related.
	
	
	@RequestMapping(value={"/","home"})
	public String home(Model model,HttpSession session){
		if(session.getAttribute("username") == null || session.getAttribute("username").toString().length() == 0) {
			return "login";
		}
		Employee emp = new Employee();
		model.addAttribute("emp", emp);
		model.addAttribute("role",session.getAttribute("role").toString());
		return "index";
	}
	
	@RequestMapping("/feedback")
	public String feedback(Model model,HttpSession session,@ModelAttribute("emp") Employee empInit){
		if(session.getAttribute("username") == null || session.getAttribute("username").toString().length() == 0) {
			return "login";
		}
		String landingPage = "";
		// feedback already submitted by user.
		if(LocalDate.now().equals(this.feedbackEndDate) || LocalDate.now().isBefore(feedbackEndDate)) {
			LocalDate date = this.feedbackDate;
			if(eFS.isFeedbackExists(empInit.getEmpCode(), date)) {
				model.addAttribute("submitted","repeat");
				landingPage = "form_submitted";
			}
			// generate a feedback page.
			else{
				eFS.generatedQuestions();
				empGlobal = eFS.startEmployeeFeedback(empInit.getEmpCode(), empInit.getDepartment(), empInit.getDesignation(), this.feedbackDate);
				eFS.saveFeedback(empGlobal);
				model.addAttribute("emp", empGlobal);
				model.addAttribute("submitted","success");
				landingPage = "feedback";
			}
		}
		else {
			model.addAttribute("submitted","overdate");
			model.addAttribute("end_date",feedbackEndDate);
			landingPage = "form_submitted";
		}
		return landingPage;
	}
	
	@RequestMapping(value = "submit_form", method=RequestMethod.POST)
	public String formSubmission(Model model, @ModelAttribute("emp") Employee emp, HttpSession session){
		if(session.getAttribute("username") == null || session.getAttribute("username").toString().length() == 0) {
			return "login";
		}
		eFS.updateFeeback(emp);
		model.addAttribute("submitted",true);
		return "form_submitted";
	}
	
	@RequestMapping(value="admin_panel")
	public String adminPanel(Model model,HttpSession session) {
		if(session.getAttribute("username") == null || session.getAttribute("username").toString().length() == 0) {
			return "login";
		}
		model.addAttribute("username",session.getAttribute("username").toString());
		return "admin_panel";
	}
	
	// Error Handling request
	@ExceptionHandler(Exception.class)
	public String handleAnyError(Model model, HttpSession session) {
		String page = "redirect:login";
		String role = session.getAttribute("role").toString();
		if(role.equals("user")) {
			page = "redirect:home";
		}
		else if(role.equals("admin")) {
			page = "redirect:admin_panel";
		}
		return page;
	}
	
	@RequestMapping("start_feedback")
	public String feedbackGenerator(Model model, HttpSession session) {
		if(session.getAttribute("username") == null || session.getAttribute("username").toString().length() == 0) {
			return "login";
		}
		model.addAttribute("date",feedbackDate);
		model.addAttribute("duration",duration);
		model.addAttribute("endDate",feedbackEndDate);
		return "feedback_generator";
	}
	
	@RequestMapping("generate_feedback_month")
	public String generateCurrentFeedback(@RequestParam("date") int month, @RequestParam("duration")int duration,HttpSession session) {
		if(session.getAttribute("username") == null || session.getAttribute("username").toString().length() == 0) {
			return "login";
		}
		int year = LocalDate.now().getYear();
		LocalDate date = LocalDate.of(year, month, 1);
		
		boolean flag = eFS.saveCurrentDate(date, duration);
		if(flag) {
			feedbackDate = date;
			this.duration = duration;
			feedbackEndDate = date.plusMonths(duration).with(TemporalAdjusters.lastDayOfMonth());
		}
		return "redirect:start_feedback";
	}
	
	@PostConstruct
	public void getCurrentDate() {
		CurrentFeedbackDate date = eFS.getCurrentFeedbackDate();
		if(date != null) {
			this.feedbackDate = eFS.getCurrentFeedbackDate().getFeedbackDate();
			this.duration = eFS.getCurrentFeedbackDate().getDuration();
			this.feedbackEndDate = eFS.getCurrentFeedbackDate().getFeedbackEndDate();
		}
	}
	
	public LocalDate getFeedbackDate() {
		return this.feedbackDate;
	}
}
