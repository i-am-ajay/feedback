<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="sf"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<meta charset="UTF-8">
<title>Feedback Details</title>
<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css" integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
<link rel="stylesheet" href = "https://cdn.datatables.net/1.10.21/css/jquery.dataTables.min.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/static_resources/css/style.css" >
<meta name="viewport" content="width=device-width, initial-scale=1">
</head>
<body class=mt-1>
	<%@ include file = "header.jsp" %>
	<div class= "container">
			<input type="hidden" id="dept" value="${dept}"/>
			<input type="hidden" id="date" value="${date}"/>
			<div>
				<table class="table table-sm" id="example" class="display mt-4" width="100%">
					<thead>
						<th>Employee Name</th>
						<th>Feedback</th>
						<th class="text-center">Reaction</th>
					</thead>
					<tbody>
						<c:forEach var="emp" items="${emp_list}">
							<tr>
								<td onClick="showEmployeeDetailedFeedback('${emp.key}')">${emp.key}</td>
								<td>${emp.value}</td>
								<td class="text-center" style="font-size:30px">${smiley_map[emp.value]}</td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
			</div>
	</div>
	
	<script src="https://code.jquery.com/jquery-3.3.1.min.js"></script>
	<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js" integrity="sha384-UO2eT0CpHqdSJQ6hJty5KVphtPhzWj9WO1clHTMGa3JDZwrnQq4sF86dIHNDz0W1" crossorigin="anonymous"></script>
	<script src="https://code.jquery.com/jquery-3.5.1.js"></script>
	<script src="https://cdn.datatables.net/1.10.21/js/jquery.dataTables.min.js"></script>
	<script src="https://use.fontawesome.com/80a486f3d9.js"></script>
	<script>
	$(document).ready(dataTableConf).ready(e =>{
		$("#home_icon").click(e =>{
			const dept = $("#dept").val();
			const date = $("#date").val();
			window.location.href="graphs?dept="+dept+"&date="+date;
		}).hover( e => {
			$("#home_icon").css({
				"cursor":"pointer"
			});
		});
	});

	function showEmployeeDetailedFeedback(empCode){
		const dept = $("#dept").val();
		const date = $("#date").val();
		window.location.href = "emp_and_feedback?emp_code="+empCode+"&feedback_date="+date+"&dept="+dept;
	}

	function dataTableConf(){
		$('#example').DataTable({
			"scrollY": "500px",
	        "scrollCollapse": true,
	        "paging": false,
	        "info": false,
	        "searching": false,
		});
	}
	/*
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
		$(document).ready(dataTableConf).ready(e =>{
			$("#home_icon").click(e =>{
				window.location.href="graphs";
			}).hover( e => {
				$("#home_icon").css({
					"cursor":"pointer"
				});
			});
		});
		$("#btn").on("click",tableDepartmentWise);*/
		
		</script>
</body>
</html>