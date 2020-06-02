<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="sf"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css" integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
<!--  <link href="${pageContext.request.contextPath}/static_resources/css/style.css" rel="stylesheet" > -->
</head>
<body class="bg-light mt-4">
	<header>
	</header>
	<div class="container">
		<div class="row my-auto">
			<div class="col mt-5">
				<div class="card">
				  <div class="card-header">
				    <h3>Select Department</h3>
				  </div>
				  <div class="card-body">
				    <div class="form-group row my-4">
					<label for="dept" class="control-label font-weight-bold col-6 col-md-4 text-center my-auto">
						Department<small class="text-danger">*</small></label>
					<input list="deptList" class="form-control form-control col-6 col-md-8"
						id="dept" name="dept" placeholder="Department" />
					<datalist id="deptList">
						<c:forEach var="item" items="${deptList}">
							<option><c:out value="${item}" /></option>
						</c:forEach>
					</datalist>
				</div>
				    <a href="#" class="btn btn-primary btn-block w-75 mx-auto mt-5" id="btn">Show Graph</a>
				    <input type="hidden" id="hidden" value="" />
				  </div>
				</div>
			</div>
			<div class="col">
				<h3 class="text-center mt-4">User feedback Pie Chart</h3>
				<div class="canvas">
					
				</div>
			</div>
		</div>
	</div>
	<!--<script src="https://code.jquery.com/jquery-3.3.1.slim.min.js" integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo" crossorigin="anonymous"></script> -->
	<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js" integrity="sha384-UO2eT0CpHqdSJQ6hJty5KVphtPhzWj9WO1clHTMGa3JDZwrnQq4sF86dIHNDz0W1" crossorigin="anonymous"></script>
	<script src="https://code.jquery.com/jquery-3.3.1.min.js"></script>
	<script src="https://d3js.org/d3.v5.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/d3-legend/2.25.6/d3-legend.min.js">
    </script>
	<script src="${pageContext.request.contextPath}/static_resources/js/index.js"></script>
	<script src="${pageContext.request.contextPath}/static_resources/js/graph.js"></script>
	<script>
		let pie_data = {};
		function pie_input(){
				$.ajax({
					type: 'POST',
					url:"${home}pie_chart",
					data:"dept="+$("#dept").val(),
					success:function(r,status,xrt){
						update(JSON.parse(r));
					}
				});
		}
		$(document).ready(pie_input)
		$("#btn").on("click",pie_input);
	</script>
</body>
</html>