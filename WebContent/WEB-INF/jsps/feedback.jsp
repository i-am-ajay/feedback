<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form" %> 
<%@ page import="com.ajay.others.QuestionBank" %> 
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Feedback Form</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/static_resource/css/style.css" >
<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css" integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">

</head>
<body>
	<%@ include file = "header.jsp" %>
	<div class="container p-2 m-auto">
	<h4 class="border-bottom m-3 text-muted pb-2">Awareness About Employee Rights, Responsibilities & Welfare Schemes</h4>
	<f:form method="POST" modelAttribute="emp" action="submit_form">
		<f:hidden path="empCode" value="${emp.empCode}"/>
		<f:hidden path="designation" vlaue="${emp.designation}" />
		<f:hidden path="department" value="${emp.department}" />
		<f:hidden path="feedbackList[0].id" value="${emp.feedbackList.get(emp.feedbackList.size()-1).id}" /> 
		<f:hidden path="feedbackList[0].id" value="${emp.feedbackList.get(0).id}" /> 
		
	<nav>
		<div class="nav nav-tabs nav-fill" id="nav-tab" role="tablist">
			<a class="nav-item nav-link active text-secondary" id="nav-home-tab" data-toggle="tab" href="#nav-home" role="tab" aria-controls="nav-home" aria-selected="true">Section 1</a>
			<a class="nav-item nav-link text-secondary" id="nav-profile-tab" data-toggle="tab" href="#nav-profile" role="tab" aria-controls="nav-profile" aria-selected="false">Section 2</a>
		</div>
	</nav>
	<!-- Tabs -->
	
	<div class="tab-content py-3 px-3 px-sm-0" id="nav-tabContent">
			<div class="tab-pane fade show active" id="nav-home" role="tabpanel" aria-labelledby="nav-home-tab">
				<c:forEach var="i" items="${emp.feedbackList.get(emp.feedbackList.size()-1).choiceList.keySet()}" varStatus="count">
				<c:if test="${count.index<13}">
					<div class="border-top border-dark p-3 mb-2">
						<c:set var="question" value="${QuestionBank.getInstance().getQuestion(i)}" />
						<blockquote class="blockquote small bg-light">${question.question}</blockquote>
						<c:forEach var="c" items="${question.choices}">
							<div class="form-check form-check-inline px-4 py-2">
								<label class="form-check-label font-weight-bold pr-2" for="radio${i}">${c}</label>
								<f:radiobutton class="form-check-input" id="radio${i}" name="${i}" path="feedbackList[${emp.feedbackList.size()-1}].choiceList[${i}].answer" value="${c}"/>
							</div>
						</c:forEach>
					</div>
				</c:if>		
				</c:forEach>
			</div>
			<div class="tab-pane fade" id="nav-profile" role="tabpanel" aria-labelledby="nav-profile-tab">
				<c:forEach var="i" items="${emp.feedbackList.get(emp.feedbackList.size()-1).choiceList.keySet()}" varStatus="count">
				<c:if test="${count.index>=13}">
					<div class="border-top border-dark p-3 mb-2">
						<c:set var="question" value="${QuestionBank.getInstance().getQuestion(i)}" />
						<blockquote class="blockquote small bg-light">${question.question}</blockquote>
						<c:forEach var="c" items="${question.choices}">
							<div class="form-check form-check-inline px-4 py-2">
								<label class="form-check-label font-weight-bold pr-2" for="radio${i}">${c}</label>
								<f:radiobutton class="form-check-input" id="radio${i}" name="${i}" path="feedbackList[${ emp.feedbackList.size()-1}].choiceList[${i}].answer" value="${c}"/>
							</div>
						</c:forEach>
					</div>	
				</c:if>	
				</c:forEach>
			</div>
		</div>
	<input class="btn btn-small btn-secondary btn-block" type="submit" />
	</f:form>
	</div>
	<script src="https://code.jquery.com/jquery-3.3.1.slim.min.js" integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo" crossorigin="anonymous"></script>
	<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js" integrity="sha384-UO2eT0CpHqdSJQ6hJty5KVphtPhzWj9WO1clHTMGa3JDZwrnQq4sF86dIHNDz0W1" crossorigin="anonymous"></script>
	<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js" integrity="sha384-JjSmVgyd0p3pXB1rRibZUAYoIIy6OrQ6VrjIEaFf/nJGzIxFDsf4x0xIM+B07jRM" crossorigin="anonymous"></script>
</body>
</html>