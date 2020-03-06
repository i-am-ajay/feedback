<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form" %> 
<%@ page import="com.ajay.others.QuestionBank" %> 
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
	<f:form method="POST" modelAttribute="emp" action="submit_form">
		<c:forEach var="i" items="${emp.feedbackList.get(0).choiceList}">
			<c:set var="question" value="${QuestionBank.getInstance().getQuestion(i.questionid)}" />
			<p>${question.question}</p>
			<c:forEach var="c" items="${question.choices}">
				<label>${c}</label>
				<f:radiobutton name="${i.questionid}" path="getQuestionChoice(0,${i.questionid})" value="${c}"/>
			</c:forEach>		
		</c:forEach>
		<input type="submit" />
	</f:form>
</body>
</html>