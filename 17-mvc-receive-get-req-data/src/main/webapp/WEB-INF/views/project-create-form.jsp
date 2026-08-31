<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"	 %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!DOCTYPE html>
<html lang="en">
<head>
	<meta charset="UTF-8">
	<title>TasksApp - Add Project</title>
	<link rel="stylesheet" href="${pageContext.request.contextPath}/static/style.css"/>
</head>
<body>
<main>
	<h1>TasksApp - Add Project</h1>

	<aside>
		<nav>
			<ul>
				<li><a href="${pageContext.request.contextPath}/tasks-app/">Home</a></li>
				<li>
					<a href="${pageContext.request.contextPath}/tasks-app/projects">
						Projects Listing
					</a>
				</li>
			</ul>
		</nav>
	</aside>

	<h2>Create a new project</h2>
	<form action="${pageContext.request.contextPath}/tasks-app/projects" method="post">
		<div class="form-control">
			<label for="projName">Project Name:</label>
			<input type="text" name="projName" id="projName"
				required="required" placeholder="Enter project name" />
		</div>
		<button type="submit">Save</button>
		&nbsp;&nbsp;
		<a href="${pageContext.request.contextPath}/tasks-app/projects">Cancel</a>
	</form>
	<br>
</main>
</body>
</html>
