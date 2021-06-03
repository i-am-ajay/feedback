<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form" %> 
<%@ page import="com.ajay.others.QuestionBank" %> 
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>Feedback Submitted</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/static_resources/css/style.css" >
<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css" integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">

</head>
<body>
	<%@ include file = "header.jsp" %>
	<div class="bg-light m-2 border-secondary mt-5">
	<c:choose> 
		<c:when test="${submitted == 'repeat'}">
			<h4 class="m-3 mt-2 pb-2">Dear Employee!! Your feedback has already been submitted to HR, you are not allowed to resubmit this feedback.</h4>
		</c:when>
		<c:when test="${submitted =='overdate'}">
			<h4 class="m-3 mt-2 pb-2">Dear Employee!! This feedback submission date is over. Last Day to submit feedback was ${end_date}, no feedback can be submitted.</h4>
		</c:when>
		<c:otherwise>
			<h4 class="m-3 mt-2 pb-2">Dear Employee!! Your feedback has been submitted to HR, thanks for your time and valuable feedback.</h4>
		</c:otherwise>
	</c:choose>
	<div>
		<h6 class="m-3 mt-1 pb-2"><span class="font-weight-bold text-danger pr-2">Note:</span>You cannot resubmit the same feedback, don't follow the same link to resubmit your feedback. </h6>
	</div>
	</div>
	<script src="https://use.fontawesome.com/80a486f3d9.js"></script>
	<script src="https://code.jquery.com/jquery-3.3.1.slim.min.js" integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo" crossorigin="anonymous"></script>
	<script>
		$("#home_icon").click( e =>{
			window.location.href = "home";
		});

		$("#home_icon").hover( e => {
			$("#home_icon").css({"cursor":"pointer"})
		})
	</script>
</body>
</html>