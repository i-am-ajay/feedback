<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="sf"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Feedback Details</title>
<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css" integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
<link rel="stylesheet" href = "https://cdn.datatables.net/1.10.21/css/jquery.dataTables.min.css">
</head>
<body class=mt-1>
	<%@ include file = "header.jsp" %>
	<div class= "container">
			<div class="form-group row">
					<label for="dept" class="control-label font-weight-bold col-6 col-md-4 text-center my-auto">
						Department<small class="text-danger">*</small></label>
					<input list="deptList" class="form-control col-6 col-md-8"
						id="dept" name="dept" placeholder="Department" />
					<datalist id="deptList" class="dropdown-menu">
						<c:forEach var="item" items="${deptList}">
							<option  class="dropdown-item" ><c:out value="${item}"/></option>
						</c:forEach>
					</datalist>
			</div>
			<div>
				<a href="#" class="btn btn-primary btn-block w-75 mx-auto mt-5" id="btn">Show Graph</a>
				<input type="hidden" id="hidden" value="" />
			</div>
			<div>
				<table id="example" class="display mt-4" width="100%"></table>
			</div>
	</div>
	
	<script src="https://code.jquery.com/jquery-3.3.1.min.js"></script>
	<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js" integrity="sha384-UO2eT0CpHqdSJQ6hJty5KVphtPhzWj9WO1clHTMGa3JDZwrnQq4sF86dIHNDz0W1" crossorigin="anonymous"></script>
	<script src="https://code.jquery.com/jquery-3.5.1.js"></script>
	<script src="https://cdn.datatables.net/1.10.21/js/jquery.dataTables.min.js"></script>
	<script>
		let pie_data = {};
		let counter = 0;
		let table = null;
		function feedback_details(){
				$.ajax({
					type: 'POST',
					url:"${home}emp_details",
					data:"dept="+$("#dept").val(),
					success:function(r,status,xrt){ // r is array of json objects.
						let arr = [];
						r = JSON.parse(r);
						console.log(r[0]);
						r.forEach(
							e=> { // e is a json object with 1 property. q : [positive_count,n_c, neu_c]
								const rowArray = [];
								const key = Object.keys(e);
								const values = e[key[0]];
								rowArray.push(key[0]);
								rowArray.push(...values);
								arr.push(rowArray);
							}
						);
						
						//data table code.
						table = $('#example').DataTable( {
							data: arr,
					        columns: [
					            { title: "Question" },
					            { title: "Positive" },
					            { title: "Neutral" },
					            { title: "Negative"  },
					        ],
					        "scrollY":        "500px",
					        "scrollCollapse": true,
					        "paging":         false,
					        "info": false,
					        "searching": false,
					        "autoFill" : true
					    } );
					}
				});
				counter += 1;
		}
		function tableDepartmentWise(){
			$.ajax({
				type: 'POST',
				url:"${home}emp_details",
				data:"dept="+$("#dept").val(),
				success:function(r,status,xrt){ // r is array of json objects.
					let arr = [];
					r = JSON.parse(r);
					console.log(r[0]);
					r.forEach(
						e=> { // e is a json object with 1 property. q : [positive_count,n_c, neu_c]
							const rowArray = [];
							const key = Object.keys(e);
							const values = e[key[0]];
							rowArray.push(key[0]);
							rowArray.push(...values);
							arr.push(rowArray);
						}
					);
					table.clear().rows.add(arr).draw();
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
		$(document).ready(feedback_details);
		$("#btn").on("click",tableDepartmentWise);
		</script>
</body>
</html>