package com.sgrh.controller;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
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
	
	// User authentication related.
	@RequestMapping("signup")
	public String signup(Model model,HttpSession session) {
		if(session.getAttribute("username") == null || session.getAttribute("username").toString().length() == 0) {
			return "login";
		}
		return "registration";
	}
	
	@RequestMapping("logout")
	public String logout(HttpSession session) {
		session.invalidate();
		return "login";
	}
	
	@RequestMapping("create_user")
	public String createUser(@RequestParam("username") String username, @RequestParam("password") String password, @RequestParam("role") String role,HttpSession session) {
		if(session.getAttribute("username") == null || session.getAttribute("username").toString().length() == 0) {
			return "login";
		}
		String page;
		boolean status = eFS.createUser(username, password, role, session.getAttribute("username").toString());
		if(status == true) {
			page = "signup";
		}
		else {
			page = "already_exists";
		}
		return page;
	}
	
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
	
	@RequestMapping("authenticate_user")
	public String authenticateUser(Model model,HttpSession session, @RequestParam(name="username") String userName, @RequestParam(name="password") String password ) {
		String page = "login";
		userName = userName.toLowerCase();
		User user = eFS.authenticateUser(userName, password);
		if( user != null && user.isActive()) {
			if(user.getPassword().equals(password.trim())) {
				session.setAttribute("username", user.getUsername());
				session.setAttribute("role", user.getRoleList().get(0).getRole());
				List<Roles> roleList = user.getRoleList();
				String role = roleList.get(0).isActiveRole()? roleList.get(0).getRole() : "login";
				role = role.toLowerCase();
				System.out.println(role);
				if(role.equals("admin")) {
					page="redirect:admin_panel";
				}
				else if(role.equals("user")) {
					page = "redirect:/";
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
		// feedback already submitted by user.
		LocalDate date = LocalDate.of(2020, 6, 1);
		if(eFS.isFeedbackExists(empInit.getEmpCode(), date)) {
			model.addAttribute("submitted",false);
			landingPage = "form_submitted";
		}
		// generate a feedback page.
		else {
			eFS.generatedQuestions();
			empGlobal = eFS.startEmployeeFeedback(empInit.getEmpCode(), empInit.getDepartment(), empInit.getDesignation());
			eFS.saveFeedback(empGlobal);
			model.addAttribute("emp", empGlobal);
			landingPage = "feedback";
		}
		return landingPage;
	}
	
	@RequestMapping(value = "submit_form", method=RequestMethod.POST)
	public String formSubmission(Model model, @ModelAttribute("emp") Employee emp){
		eFS.updateFeeback(emp);
		model.addAttribute("submitted",true);
		return "form_submitted";
	}
	
	@RequestMapping(value="admin_panel")
	public String adminPanel(Model model,HttpSession session) {
		model.addAttribute("username",session.getAttribute("username").toString());
		return "admin_panel";
	}
	
	// Error Handling request
	@ExceptionHandler
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
}
