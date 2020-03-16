package com.ajay.others;
import java.util.HashMap;
import java.util.Map;

import com.conf.component.Questions;

public class QuestionBank {
	Map<Integer, Questions> questionMap = new HashMap<>();
	private static QuestionBank questionBank = new QuestionBank();
	
	private QuestionBank() {
		setQuestions();
	}
	
	public static QuestionBank getInstance() {
		return questionBank;
	}
	
	public Questions getQuestion(Integer qNum) {
		System.out.println("Question called");
		return questionMap.get(qNum);
	}
	
	public Map<Integer, Questions> getQuestionMap() {
		return questionMap;
	}

	public void setQuestionMap(Map<Integer, Questions> questionMap) {
		this.questionMap = questionMap;
	}

	private void setQuestions() {
		createQuestion(1,"Are you aware that the Hospital has frame Employee rights and responsibilities?",new String[]{"Can't say","Yes","No"});
		createQuestion(2,"Is there a sexual harassment committe in the hospital?",new String[]{"Can't say","Yes","No"});
		createQuestion(3,"How do you represent or put up your grievance if any?",new String[]{"To HR thru HOD","Directly to HR","Others"});
		createQuestion(4,"Are you aware that it is an employee's responsibility to get his/her leave approved in advance and not avail it as a right?",new String[]{"Yes","No"});
		createQuestion(5,"Are you aware that the hospital provides free of charge medical facility (both OPC & IPD) to its employees?",new String[]{"Yes","No"});
		createQuestion(6,"Are you aware that Gratuity is payable to eligible employee after they have served for certain period satisfactorily with the Hospital?", new String[] {"Yes","No"});
		createQuestion(7,"Are you aware that each employee has a unique identification number called the GAA number?",new String[]{"Yes","No"});
		createQuestion(8,"Is the Hospital certified by NABH/NABL/ISO 9001/EMS/OHSAS?", new String[] {"Can't say","Yes","No"});
		createQuestion(9,"Is it an employee's responsibility to safeguard the hospital's assests like machines, furniture, medicines etc?",new String[]{"True","False"});
		
		createQuestion(11,"All employees must be aware about the hospital's mission & vision statements.", new String[]{"True","False"});
		createQuestion(12,"Employees need to be courteous, at all times, to the public that they serve.", new String[]{"True","False"});
		createQuestion(13,"The \"Code Bule\" is for Fire.", new String[]{"True","False"});
		createQuestion(14,"The \"Code Violet\" is for Violence.", new String[]{"True","False"}); 
		// Employee statisfaction survey
		
		createQuestion(15,"The organization is the leader in the health care industry in Delhi.", new String[]{"Agreed","Some What Agree","Don't Agree"});
		createQuestion(16,"The experience I gain here improves my position in the market due to the reputation of the organization.", new String[]{"Agreed","Some What Agree","Don't Agree"});
		createQuestion(17,"The organization is a good place to work in terms of latest systems and practices.", new String[]{"Agreed","Some What Agree","Don't Agree"});
		createQuestion(18,"I am potimistic about my future success in this organization.", new String[]{"Agreed","Some What Agree","Don't Agree"});
		createQuestion(19,"My compensation matches with my job responsibilities.", new String[]{"Agreed","Some What Agree","Don't Agree"});
		createQuestion(20,"Mycompensation matches with similarly placed hospitals.", new String[]{"Agreed","Some What Agree","Don't Agree"});
		createQuestion(21,"My job responsibilities are reasonable enough for me to manage.", new String[]{"Agreed","Some What Agree","Don't Agree"});
		createQuestion(22,"My job gives me a sense of personal accomplishment.", new String[]{"Agreed","Some What Agree","Don't Agree"});
		createQuestion(23,"I get due recognition of my contributions.", new String[]{"Agreed","Some What Agree","Don't Agree"});
		createQuestion(24,"I get enough opportunity for learning and professional upgradation in the department/organization.", new String[]{"Agreed","Some What Agree","Don't Agree"});
		createQuestion(25,"I get sufficient time & opportunity to sharpen my skills to take higher responsibility.", new String[]{"Agreed","Some What Agree","Don't Agree"});
		createQuestion(26,"I get enough opportunity for learning and professional upgradation in the department/organization.", new String[]{"Agreed","Some What Agree","Don't Agree"});
		createQuestion(27,"There is a degree of professionalism in my Co-workers.", new String[]{"Agreed","Some What Agree","Don't Agree"});
		createQuestion(28,"There is a team spirit amongst the co-workers.", new String[]{"Agreed","Some What Agree","Don't Agree"});
		createQuestion(29,"There is a positive energy/morale in my co-workers.", new String[]{"Agreed","Some What Agree","Don't Agree"});
		createQuestion(30,"I can access my superiors easily.", new String[]{"Agreed","Some What Agree","Don't Agree"});
		createQuestion(31,"I am able to maintain a reasonable balance between my job and family life.", new String[]{"Agreed","Some What Agree","Don't Agree"});
		createQuestion(32,"I am satisfied with the working hours/break timings.", new String[]{"Agreed","Some What Agree","Don't Agree"});
		createQuestion(33,"I am satisfied with overall job security.", new String[]{"Agreed","Some What Agree","Don't Agree"});
		createQuestion(34,"Adequate Medical Benefits are given to employees.", new String[]{"Agreed","Some What Agree","Don't Agree"});
		createQuestion(35,"The various Loans and Advances give to Employees are big help to the employees.", new String[]{"Agreed","Some What Agree","Don't Agree"});	
	}
	public Questions createQuestion(int id, String name, String ...choices) {
		Questions q = new Questions();
		q.setId(id);
		q.setQuestion(name);
		for(String choice: choices) {
			q.getChoices().add(choice);
		}
		questionMap.put(q.getId(),q);
		return q;
	}
}
