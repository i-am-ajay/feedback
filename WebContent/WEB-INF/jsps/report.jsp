<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="sf"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>Report Dashboard</title>
<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css" integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
</head>
<body class="bg-light mt-1">
	<%@ include file = "header.jsp" %>
	<div class="container">
		<div class="row my-auto">
			<div class="col-md-5 col">
				<div class="card mt-2">
				  <div class="card-header">
				    <h6>Select Department</h6>
				  </div>
				  <div class="card-body">
				    <div class="form-group row">
						<label for="dept" class="control-label font-weight-bold col-6 col-md-4 text-right my-auto">
							Department<small class="text-danger">*</small></label>
						<input list="deptList" class="form-control form-control-sm col-6 col-md-8"
							id="dept" name="dept" placeholder="Department" />
						<datalist id="deptList">
							<c:forEach var="item" items="${deptList}">
								<option><c:out value="${item}" /></option>
							</c:forEach>
						</datalist>
					</div>
					<div class="form-group row">
						<label for="dept" class="control-label font-weight-bold col-6 col-md-4 text-right my-auto">
							Start Date</label>
						<input type="date" class="form-control form-control-sm col-6 col-md-8"
							id="start" name="start_date"/>
					</div>
					<div class="form-group row">
						<label for="dept" class="control-label font-weight-bold col-6 col-md-4 text-right my-auto">
							End Date</label>
						<input type="date" class="form-control form-control-sm col-6 col-md-8"
							id="end" name="end_date"/>
					</div>
				    <a href="#" class="btn btn-primary btn-block w-75 mx-auto mt-2" id="btn">Show Graph</a>
				    <input type="hidden" id="hidden" value="" />
				  </div>
				</div>
			</div>
			<div class="col-md-7 col">
				<h5 class="text-center mt-3">Department Feedback Pie Chart</h5>
				<div class="canvas text-center">
					
				</div>
			</div>
		</div>
		<!-- Row 2 -->
		<div class="row my-auto">
			<div class="col-md-5 col mt-2">
				<div class="card">
				  <div class="card-header">
				    <h6>Department-wise Summary</h6>
				  </div>
				  <div class="card-body">
				    	<div class="row">
				    		<div class="col-sm-10"><h6>Total Employees</h6></div>
				    		<div class="col-sm-2"><h6><span class="badge badge-primary py-2  w-100" id=total>20</span></h6></div>
				    	</div>
						<div class="row">
							<div class="col-sm-10"><h6>Feedback Submitted By</h6></div>
				    		<div class="col-sm-2"><h6><span class="badge badge-success py-2  w-100" id="feed">14</span></h6></div>
						</div>
						<div class="row">
							<div class="col-sm-10"><h6>Feedback Not Submitted By</h6></div>
				    		<div class="col-sm-2"><h6><span class="badge badge-danger py-2  w-100" id="no_feed">6</span></h6></div>
						</div>
						<div class="mt-3 col"><a href="details" class=" btn btn-xs btn-primary btn-block w-75 mx-auto">Detailed Feedback</a></div>
					</div>
				  </div>
			</div>
			<div class="col-md-7 col">
				<h5 class="text-center mt-4">User Feedback Bar Chart</h5>
				<div class="canvas_bar ml-5 pl-5">
					
				</div>
			</div>
		</div>
	</div>
	<!--<script src="https://code.jquery.com/jquery-3.3.1.slim.min.js" integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo" crossorigin="anonymous"></script> -->
	<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js" integrity="sha384-UO2eT0CpHqdSJQ6hJty5KVphtPhzWj9WO1clHTMGa3JDZwrnQq4sF86dIHNDz0W1" crossorigin="anonymous"></script>
	<script src="https://code.jquery.com/jquery-3.3.1.min.js"></script>
	<script src="https://d3js.org/d3.v5.min.js"></script>
	<script src="//d3js.org/d3-scale-chromatic.v0.3.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/d3-legend/2.25.6/d3-legend.min.js"></script>
    <script src="https://use.fontawesome.com/80a486f3d9.js"></script>
   
	<script src="${pageContext.request.contextPath}/static_resources/js/index.js"></script>
	<script src="${pageContext.request.contextPath}/static_resources/js/graph.js"></script>
	<script src="${pageContext.request.contextPath}/static_resources/js/bar_chart.js"></script>
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
		function emp_count(){
			$.ajax({
				type: 'POST',
				url: '${home}summary',
				data: "dept="+$("#dept").val(),
				success: function(result,status,xrt){
					result = JSON.parse(result);
					$("#total").text(result.total);
					$("#feed").text(result.feed);
					$("#no_feed").text(result.no_feed);
				}
			})
		}
		function bar_chart(){
			$.ajax({
				type : 'POST',
				url : '${home}barchart',
				data : "dept="+$('#dept').val(),
				success: function(result, status, xrt){
					console.log(result);
					update_bar(JSON.parse(result));
				}
			})
		}
		$(document)
			.ready(pie_input)
			.ready(emp_count)
			.ready(bar_chart)
			.ready(e => {
				$("#home_icon").click( e =>{
					window.location.href = "admin_panel";
				});
				$("#home_icon").hover(() =>{
					$("#home_icon").css({"cursor":"pointer"});
				})
			});
		$("#btn").on("click",pie_input).on("click",emp_count).on("click",bar_chart);
	</script>
</body>
</html>