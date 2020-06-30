package com.sgrh.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

import com.conf.component.Employee;
import com.conf.component.Feedback;
import com.conf.component.Roles;
import com.conf.component.User;
import com.sgrh.service.EmployeeFeedbackService;
import com.sgrh.service.PISService;
import com.sgrh.service.ReportService;

@Controller
@SessionAttributes({"emp"})
public class MainController{
	@Autowired
	EmployeeFeedbackService eFS;
	@Autowired
	ReportService service;
	
	@Autowired
	PISService pisService;
	
	Employee empGlobal;
	
	int feedbackId;
	
	Employee emp;
	
	@RequestMapping("/")
	public String home(Model model,HttpSession session){
		System.out.println("login form is needed");
		if(session.getAttribute("username") == null || session.getAttribute("username").toString().length() == 0) {
			return "login";
		}
		Employee emp = new Employee();
		model.addAttribute("emp", emp);
		return "index";
	}
	
	@RequestMapping("authenticate_user")
	public String authenticateUser(Model model,HttpSession session, @RequestParam(name="user_name") String userName, @RequestParam(name="password") String password ) {
		String page = "login";
		User user = eFS.authenticateUser(userName, password);
		if( user != null && user.isActive()) {
			if(user.getPassword().equals(password.trim())) {
				session.setAttribute("username", user.getUsername());
				List<Roles> roleList = user.getRoleList();
				String role = roleList.get(0).isActive()? roleList.get(0).getRole() : "login";
				if(role.equals("admin")) {
					page="redirect:graphs";
				}
				else if(role.equals("user")) {
					page = "redirect://";
				}
				else {
					page = "login";
				}
			}
		}
		return page;
	}
	
	@RequestMapping("/feedback")
	public String feedback(Model model,HttpSession session,@ModelAttribute("emp") Employee empInit){
		if(session.getAttribute("username") == null || session.getAttribute("username").toString().length() == 0) {
			return "login";
		}
		String landingPage = "";
		if(eFS.isFeedbackExists(empInit.getEmpCode())) {
			model.addAttribute("submitted",false);
			landingPage = "form_submitted";
		}
		else {
			eFS.generatedQuestions();
			empGlobal = eFS.startEmployeeFeedback(empInit.getEmpCode(), empInit.getDepartment(), empInit.getDesignation());
			eFS.saveFeedback(empGlobal);
			for(Feedback feedback: empGlobal.getFeedbackList()) {
				if(feedback != null)
					System.out.println(feedback.getId());
				else
					System.out.println(feedback);
			}
			//System.out.println("Size of list: "+empGlobal.getFeedbackList().get(0).getChoiceList().size());
			//System.out.println("Feedback Employee"+empGlobal.getFeedbackList().get(0).getEmployee().getEmpCode());
			//feedbackId = empGlobal.getFeedbackList().get(0).getId();
			model.addAttribute("emp", empGlobal);
			/*for(Feedback feed : empGlobal.getFeedbackList()) {
				System.out.println("Feedback ID"+feed.getId()+"Choice :1"+feed.getChoiceList().get(1).getAnswer());
			}*/
			landingPage = "feedback";
		}
		return landingPage;
	}
	
	@RequestMapping(value = "submit_form", method=RequestMethod.POST)
	public String formSubmission(Model model, @ModelAttribute("emp") Employee emp){
		//System.out.println(emp.getFeedbackList().get(0).getEmployee().getEmpCode());
		//System.out.println(emp.getFeedbackList().get(0).getChoiceList().get(2).getQuestionid());
		//System.out.println("Feedback Size"+emp.getFeedbackList().size());
		eFS.updateFeeback(emp);
		model.addAttribute("submitted",true);
		return "form_submitted";
	}
	
	
}
