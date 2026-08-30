<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
	<meta charset="UTF-8">
	<title>TasksApp - Home</title>
	<link rel="stylesheet" href="${pageContext.request.contextPath}/static/style.css"/>
</head>
<body>
<main>
	<h1>TasksApp - Welcome</h1>
	
	<h2>Self service menu</h2>
	<p>Select an option below to proceed:</p>
	
	<ul>
		<li><a href="${pageContext.request.contextPath}/tasks-app/departments">Show Departments</a></li>
		<li><a href="${pageContext.request.contextPath}/tasks-app/users">Show Users</a></li>
		<li><a href="${pageContext.request.contextPath}/tasks-app/projects">Show Projects</a></li>
		<li><a href="${pageContext.request.contextPath}/tasks-app/tasks">Show Tasks</a></li>
	</ul>
</main>
</body>
</html>
