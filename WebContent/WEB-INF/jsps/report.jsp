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
<body>
	<header>
		
	</header>
	<div class="container">
		<div class="row">
			<div class="col">
				<div class="card">
				  <div class="card-header">
				    Select Department
				  </div>
				  <div class="card-body">
				    <div class="form-group row">
					<label for="dept" class="control-label font-weight-bold col-6 col-md-4">
						Department<small class="text-danger">*</small></label>
					<input list="deptList" class="form-control form-control-sm col-6 col-md-8"
						id="dept" name="dept" placeholder="Department" />
					<datalist id="deptList">
						<c:forEach var="item" items="${deptList}">
							<option><c:out value="${item}" /></option>
						</c:forEach>
					</datalist>
				</div>
				    <a href="#" class="btn btn-primary">Show Graph</a>
				    <input type="hidden" id="hidden" value="${data}" />
				  </div>
				</div>
			</div>
			<div class="col">
				<div class="canvas">
					
				</div>
			</div>
		</div>
	</div>
	<script src="https://code.jquery.com/jquery-3.3.1.slim.min.js" integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo" crossorigin="anonymous"></script>
	<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js" integrity="sha384-UO2eT0CpHqdSJQ6hJty5KVphtPhzWj9WO1clHTMGa3JDZwrnQq4sF86dIHNDz0W1" crossorigin="anonymous"></script>

	<script src="${pageContext.request.contextPath}/static_resources/js/index.js"></script>
	<script src="${pageContext.request.contextPath}/static_resources/js/graph.js"></script>

</body>
</html>