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
<meta name="viewport" content="width=device-width, initial-scale=1">
</head>
<body class=mt-1>
	<%@ include file = "header.jsp" %>
	<div class="container p-2 m-auto">
		<h4 class="border-bottom m-3 text-muted pb-2">Employee Details</h4>
		<f:form method="POST" modelAttribute="emp" action="feedback">
		  <div class="form-row">
		    <div class="form-group col-md-4">
		      <label for="empcode" class="font-weight-bold">Employee Code <span class="text-danger">*</span></label>
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
		  <!-- Alert -->
		  <div class="alert alert-danger alert-dismissible fade" role="alert" id="alert_id">
  			<span id="text">Replaceable Text</span>
  			<!--  <button type="button" id="alert_" class="close" data-dismiss="alert" aria-label="Close">
    			<span aria-hidden="true">&times;</span>
  			</button>-->
		  </div>
		   <input type="submit" class="btn btn-small btn-secondary btn-block" value="Start Feedback"/>
		   <input type="hidden" id="role" value="${role}" />
		</f:form>
		
		<!-- <div class="mt-3">
			<p class="text-center"><i id="home_icon" class="fa fa-home fa-2x" aria-hidden="true"></i></p>
		</div> -->
	</div>
	
	<!-- <script src="https://code.jquery.com/jquery-3.3.1.slim.min.js" integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo" crossorigin="anonymous"></script> -->
	<script src="https://code.jquery.com/jquery-3.3.1.min.js"></script>
	<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js" integrity="sha384-UO2eT0CpHqdSJQ6hJty5KVphtPhzWj9WO1clHTMGa3JDZwrnQq4sF86dIHNDz0W1" crossorigin="anonymous"></script>
	<script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js" integrity="sha384-JZR6Spejh4U02d8jOt6vLEHfe/JQGiRRSQQxSfFWpi1MquVdAyjUar5+76PVCmYl" crossorigin="anonymous"></script>
	<script src="https://use.fontawesome.com/80a486f3d9.js"></script>
	<script>
		$(document).ready(
			function(){
				$("#empcode").attr("required","true");
				$("#department").attr("readonly","readonly");
				$("#designation").attr("readonly","readonly");
			}
		).ready(e => {
			const role = $("#role").val();
			if(role.toLowerCase() == "user"){
				$("#home_icon").hide();
			}
		}).ready( e =>{
			$("#home_icon").click( e => {
				window.location.href = "admin_panel";
			}).hover( e =>{
				$("#home_icon").css({
					"cursor": "pointer"
				});
			})
		});
		$('#empcode').on("focusout",function(e){
			if(!this.value){
				$("#department").val("");
				$("#designation").val("");
			}
			else{
				// check for valid employee code.
				// regular pattren validation
				const $reg = /GA[A,B]\d{4}$/;
				const emp = $('#empcode').val();
				const text = "Not a valid employee code."
				if(!emp.toUpperCase().match($reg)){
					$('#text').text(text);
					$("#department").val(null);
					$("#designation").val(null);
					$('#alert_id').addClass("show");
				}
				else{
					$('#alert_id').removeClass("show");
					$('#empcode').val(emp.toUpperCase());
					$.ajax({
						type: "POST",
						url: "${home}pisEmp",
						data: 'empCode='+this.value,
						success: function(result, status, xhr){
							if(result == null || result == ""){
								$('#text').text("Emp Code does not exists.");
								$('#alert_id').addClass("show");
								$("#department").val("");
								$("#designation").val("");
							}
							else{
								result=JSON.parse(result);
								$("#empcode").val(result.code);
								$("#department").val(result.dept);
								$("#designation").val(result.desig);
							}
						},
						error: function(result, status, xhr){
							
						}
					})
				}
			}
		});
		
	</script>
</body>
</html>