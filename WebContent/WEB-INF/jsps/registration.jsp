<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form" %> 

<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<title>Insert title here</title>
	<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css" integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
	<link rel="stylesheet" href="${pageContext.request.contextPath}/static_resource/css/bootstrap.min.css" />
	<meta name="viewport" content="width=device-width, initial-scale=1">
</head>
<body>
	<%@ include file = "header.jsp" %>
	<div class="container mt-3">
	<div class="card bg-light">
	<article class="card-body mx-auto" style="max-width: 350px;">
		<h4 class="card-title text-center display-4 border-bottom border-danger py-2 my-3">Create User</h4>
		<form method="POST" action="create_user">
		<div class="form-group input-group">
			<div class="input-group-prepend">
			    <span class="input-group-text"> <i class="fa fa-user"></i> </span>
			 </div>
	        <input name="username" class="form-control" placeholder="Username" type="text" required>
	    </div> <!-- form-group// -->
	   <!--  <div class="form-group input-group">
	    	<div class="input-group-prepend">
			    <span class="input-group-text"> <i class="fa fa-envelope"></i> </span>
			 </div>
	        <input name="" class="form-control" placeholder="Email address" type="email">
	    </div> form-group// 
	    <div class="form-group input-group">
	    	<div class="input-group-prepend">
			    <span class="input-group-text"> <i class="fa fa-phone"></i> </span>
			</div>
			<select class="custom-select" style="max-width: 120px;">
			    <option selected="">+971</option>
			    <option value="1">+972</option>
			    <option value="2">+198</option>
			    <option value="3">+701</option>
			</select>
	    	<input name="" class="form-control" placeholder="Phone number" type="text">
	    </div> <!-- form-group// -->
	    <div class="form-group input-group">
	    	<div class="input-group-prepend">
			    <span class="input-group-text"> <i class="fa fa-building"></i> </span>
			</div>
			<select name="role" class="form-control">
				<option value="User"> Select Role</option>
				<option value="Admin">Admin</option>
				<option value="User">User</option>
			</select>
		</div> <!-- form-group end.// -->
	    <div class="form-group input-group">
	    	<div class="input-group-prepend">
			    <span class="input-group-text"> <i class="fa fa-lock"></i> </span>
			</div>
	        <input id="password" name="password" class="form-control" placeholder="Create password" type="password">
	    </div> <!-- form-group// -->
	    <!--  
	    <div class="form-group input-group">
	    	<div class="input-group-prepend">
			    <span class="input-group-text"> <i class="fa fa-lock"></i> </span>
			</div>
	        <input id="rpassword" name="repeat_password" class="form-control" placeholder="Repeat password" type="password">
	    </div> <!-- form-group// -->                                      
	    <div class="form-group"> 
	        <button type="submit" class="btn btn-primary btn-block"> Create User  </button>
	    </div> <!-- form-group// -->                                                              
	</form>
	</article>
	</div> <!-- card.// -->
	<!-- <div>
		<p class="text-center mt-2"><i id="home_icon" class="fa fa-home fa-2x text-center" aria-hidden="true"></i></p>
	</div> -->
	</div> 
	<!--container end.//-->
	<script src="https://use.fontawesome.com/80a486f3d9.js"></script>
	<script src="https://code.jquery.com/jquery-3.3.1.slim.min.js" integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo" crossorigin="anonymous"></script>
	<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js" integrity="sha384-UO2eT0CpHqdSJQ6hJty5KVphtPhzWj9WO1clHTMGa3JDZwrnQq4sF86dIHNDz0W1" crossorigin="anonymous"></script>
	<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js" integrity="sha384-JjSmVgyd0p3pXB1rRibZUAYoIIy6OrQ6VrjIEaFf/nJGzIxFDsf4x0xIM+B07jRM" crossorigin="anonymous"></script>
	<script src="https://use.fontawesome.com/80a486f3d9.js"></script>
	<script>
		$(document).ready(e =>{
			$("#home_icon").click(
				(e)=>{
					window.location.href = "admin_panel";
				})
		}).ready( e => {
			$("#home_icon").hover(
				(e) => {
					$("#home_icon").css({"cursor":"pointer"});
			})
		});
	</script>
</body>
</html>