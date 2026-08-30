<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"	 %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!DOCTYPE html>
<html lang="en">
<head>
	<meta charset="UTF-8">
	<title>TasksApp - Add Department</title>
	<link rel="stylesheet" href="${pageContext.request.contextPath}/static/style.css"/>
</head>
<body>
<main>
	<h1>TasksApp - Add Department</h1>

	<aside>
		<nav>
			<ul>
				<li><a href="${pageContext.request.contextPath}/tasks-app/">Home</a></li>
				<li>
					<a href="${pageContext.request.contextPath}/tasks-app/departments">
						Departments Listing
					</a>
				</li>
			</ul>
		</nav>
	</aside>

	<h2>Create a new department</h2>
	<form action="${pageContext.request.contextPath}/tasks-app/departments" method="post">
		<div class="form-control">
			<label for="deptName">Department Name:</label>
			<input type="text" name="deptName" id="deptName"
				required="required" placeholder="Enter dept name" />
		</div>
		<button type="submit">Save</button>
		&nbsp;&nbsp;
		<a href="${pageContext.request.contextPath}/tasks-app/departments">Cancel</a>
	</form>
	<br>
</main>
</body>
</html>
