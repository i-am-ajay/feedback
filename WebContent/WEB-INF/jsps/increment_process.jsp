<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="sf"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>Increment Report</title>
<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css" integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
	<link rel="stylesheet" href="${pageContext.request.contextPath}/static_resources/css/style.css" >
	<link rel="stylesheet" href="${pageContext.request.contextPath}/static_resources/css/bootstrap_min.css" >
	<link rel="stylesheet" href="${pageContext.request.contextPath}/static_resources/css/datatable_min.css" >
	<link rel="stylesheet" href="https://cdn.datatables.net/1.10.21/css/jquery.dataTables.min.css">
</head>
<body class="bg-light mt-1">
	<%@ include file = "header_index.jsp" %>
	
	<form method="POST" action="due_increment">
		<div class="row m-3">
			<div class='col'>
				<div class="form-group row" >
					<div class='col-3 form-control-label font-weight-bold text-right'>Month</div>
					<div class='col-9'>
						<select class="form-control form-control-sm" name="month">
							<c:forEach var="mth" items="${month}">
								<c:choose>
									<c:when test="${mth.equals(current_month)}">
										<option selected>${mth}</option>
									</c:when>
									<c:otherwise>
										<option>${mth}</option>
									</c:otherwise>
								</c:choose>
							</c:forEach>
						</select>
						</div>
				</div>
			</div>
			<div class='col'>
				<div class="form-group row" >
					<div class='col-3 form-control-label font-weight-bold text-right'> Year</div>
					<div class='col-9'>
						<select class="form-control form-control-sm" name="year">
							<c:forEach var="yr" items="${year}">
								<c:choose>
									<c:when test="${yr.equals(current_year)}">
										<option selected>${yr}</option>
									</c:when>
									<c:otherwise>
										<option>${yr}</option>
									</c:otherwise>
								</c:choose>
							</c:forEach>
						</select>
						</div>
				</div>
			</div>
			<div class='col'>
				<input class="btn btn-sm btn-primary btn-block w-75" type="submit" value="Get Increment"/>
			</div>
		</div>
	</form>
	
	<c:choose>
		<c:when test="${status.equalsIgnoreCase('success') }">
			<h6 class="border-bottom mb-1 text-muted pb-2" id="form_title"></h6>
			<div id="table" class="container mx-auto">
				<table id="example" class="display compact cell-border" style="width:100%">
		        	<thead>
		            	<tr>
		            		<th>S.No</th>
		                	<th>Emp Code</th>
			                <th>Employee Name</th>
			                <th>Level</th>
			                <th>Old Basic</th>
			                <th>New Basic</th>
			                <th>Date Of Increment</th>
			                <th>Next Date of Increment</th>
			            </tr>
		        	</thead>
		        	<tbody>
		        		<c:forEach var="empList" items="${emp_list}" varStatus="index" >
	        			<tr class="py-2 text-justify">
	        				<td class="demo">${index.count}</td>
	        				<td class="demo">${empList[0][0]}</td>
		        			<td class="demo">${empList[0][1]}</td>
		        			<td class="demo">${empList[0][3]} </td>
		        			<td class="demo">${empList[0][2]}</td>
		        			<td class="demo">${empList[1]}</td>
		        			<td id="address">${empList[0][4]}</td>
		        			<td id="address">${empList[0][5]}</td>
		        			</tr>
		        		</c:forEach>
		        	</tbody>
	    		</table>	
			</div>
		</c:when>
		<c:otherwise>
			<h5>&emsp;There is some issue contact IT Department.</h5>
		</c:otherwise>
	</c:choose>
	
	<!-- <script src="https://code.jquery.com/jquery-3.3.1.slim.min.js" integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo" crossorigin="anonymous"></script> -->
	
<script src="${pageContext.request.contextPath}/static_resources/js/jquery_3.5.1_min.js"></script>
	<script src="${pageContext.request.contextPath}/static_resources/js/bootstrap_min.js"></script>
	<script src="${pageContext.request.contextPath}/static_resources/js/popper.js"></script>
	<script src="${pageContext.request.contextPath}/static_resources/js/bootstrap_max_cdn_min.js"></script>
	<script src="${pageContext.request.contextPath}/static_resources/js/font_awesome.js"></script>
	<script src="${pageContext.request.contextPath}/static_resources/js/data_table.js"></script>
	<script type="text/javascript" src="https://cdn.datatables.net/v/bs4/dt-1.10.21/af-2.3.5/b-1.6.3/b-html5-1.6.3/datatables.min.js"></script>
	<script>
	var table = null;
		$(document).ready(function() {
			table = $('#example').DataTable( {
		        "scrollY": 230,
		        "scrollX" : true,
		        "info" : false,
		        "autoWidth" : false,
		        "dom": 'Bfrtip',
		        "buttons": [
		        	 {extend : 'copyHtml5', className : ' btn btn-sm px-4'}
		        ]
		        });
		})
</body>
</html>