<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form" %> 
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Enter Employee Details</title>
<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css" integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js" integrity="sha384-JjSmVgyd0p3pXB1rRibZUAYoIIy6OrQ6VrjIEaFf/nJGzIxFDsf4x0xIM+B07jRM" crossorigin="anonymous"></script>
</head>
<body>
	<%@ include file = "header.jsp" %>
	<div class="container p-2 m-auto">
		<h4 class="border-bottom m-3 text-muted pb-2">Employee Details</h4>
		<f:form method="POST" modelAttribute="emp" action="feedback">
		  <div class="form-row">
		    <div class="form-group col-md-4">
		      <label for="empcode" class="font-weight-bold">Employee Code</label>
		      <f:input class="form-control form-control-sm" id="empcode" placeholder="Employee Code" path="empCode"/>
		    </div>
		    <div class="form-group col-md-4">
		      <label for="designation" class="font-weight-bold">Designation</label>
		      <f:input class="form-control form-control-sm" id="designation" placeholder="Designation" path="designation"/>
		    </div>
		    <div class="form-group col-md-4">
		      <label for="department" class="font-weight-bold">Department</label>
		      <f:input class="form-control form-control-sm" id="department" placeholder="Department" path="department"/>
		    </div>
		  </div>
		   <input type="submit" class="btn btn-small btn-secondary btn-block" value="Start Feedback"/>
		</f:form>
	</div>
	
	<!-- <script src="https://code.jquery.com/jquery-3.3.1.slim.min.js" integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo" crossorigin="anonymous"></script> -->
	<script src="https://code.jquery.com/jquery-3.3.1.min.js"></script>
	<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js" integrity="sha384-UO2eT0CpHqdSJQ6hJty5KVphtPhzWj9WO1clHTMGa3JDZwrnQq4sF86dIHNDz0W1" crossorigin="anonymous"></script>
	<script>
		$(document).ready(
			function(){
				$("#empcode").attr("required","true");
				$("#department").attr("readonly","readonly");
				$("#designation").attr("readonly","readonly");
			}
		);
		$('#empcode').on("focusout",function(e){
			if(!this.value){
				$("#department").val("");
				$("#designation").val("");
			}
			else{
				$.ajax({
					type: "POST",
					url: "${home}pisEmp",
					data: 'empCode='+this.value,
					success: function(result, status, xhr){
						result=JSON.parse(result);
						$("#empcode").val(result.code);
						$("#department").val(result.dept);
						$("#designation").val(result.desig);
					},
					error: function(result, status, xhr){
						console.log(result);
					}
				})
			}
		});
		
	</script>
</body>
</html>