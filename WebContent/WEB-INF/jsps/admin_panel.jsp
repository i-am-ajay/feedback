<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form" %> 
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/static_resource/css/style.css" >
<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css" integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
<meta name="viewport" content="width=device-width, initial-scale=1">
</head>
<body>
	<%@ include file = "header.jsp" %>
	<div class="container mt-3">
	<div class="row mt-5">
		<div class="card bg-light col-sm-12 col-md-4 my-1 mx-3 py-1" id="create_user">
		<article class="card-body mx-auto">
			<h4 class="card-title text-center display-4 border-bottom border-danger py-2 my-3">Create User</h4>
			<p class="text-center mt-4"><i class="fa fa-user-circle fa-3x" aria-hidden="true"></i></p>
		</article>
		</div>
		<div class="card bg-light col-sm-12 col-md-4 my-1 mx-3 py-1" id="graphs">
		<article class="card-body mx-auto">
			<h4 class="card-title text-center display-4 border-bottom border-danger py-2 my-3">Analysis</h4>
			<p class="text-center mt-4"><i class="fa fa-line-chart fa-3x" aria-hidden="true"></i></p>
		</article>
		</div>
		
		<div class="card bg-light col-md-4 my-1 mx-3 py-1" id="feedback">
		<article class="card-body mx-auto">
			<h4 class="card-title text-center display-4 border-bottom border-danger py-2 my-3">Feedback</h4>
			<p class="text-center mt-4"><i class="fa fa-comments-o fa-3x" aria-hidden="true"></i></p>	
		</article>
		</div>
	</div>
	</div> 
	<!--container end.//-->
	<script src="https://use.fontawesome.com/80a486f3d9.js"></script>
	<script src="https://code.jquery.com/jquery-3.3.1.slim.min.js" integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo" crossorigin="anonymous"></script>
	<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js" integrity="sha384-UO2eT0CpHqdSJQ6hJty5KVphtPhzWj9WO1clHTMGa3JDZwrnQq4sF86dIHNDz0W1" crossorigin="anonymous"></script>
	<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js" integrity="sha384-JjSmVgyd0p3pXB1rRibZUAYoIIy6OrQ6VrjIEaFf/nJGzIxFDsf4x0xIM+B07jRM" crossorigin="anonymous"></script>
	<script>
		$(".card").hover(e => {
			$(".card").css({'cursor':'pointer'});
		});

		// go to create user
		$("#create_user").click(e => {
			window.location.href ="signup";
		});
		
		// go to graphs
		$("#graphs").click( e=>{
			window.location.href ="graphs";
		});
		// go to feedback
		$("#feedback").click( e=>{
			window.location.href ="home";
		});

		$(document).ready( e=>{
			$("#home_icon").hide();
		});
	</script>
</body>
</html>