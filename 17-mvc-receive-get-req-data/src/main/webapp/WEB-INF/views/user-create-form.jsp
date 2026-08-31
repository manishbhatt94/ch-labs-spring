<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"	 %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!DOCTYPE html>
<html lang="en">
<head>
	<meta charset="UTF-8">
	<title>TasksApp - Add User</title>
	<link rel="stylesheet" href="${pageContext.request.contextPath}/static/style.css"/>
</head>
<body>
<main>
	<h1>TasksApp - Add User</h1>

	<aside>
		<nav>
			<ul>
				<li><a href="${pageContext.request.contextPath}/tasks-app/">Home</a></li>
				<li>
					<a href="${pageContext.request.contextPath}/tasks-app/users">
						Users Listing
					</a>
				</li>
			</ul>
		</nav>
	</aside>

	<h2>Create a new user</h2>
	<form action="${pageContext.request.contextPath}/tasks-app/users" method="post">
		<div class="form-control">
			<label for="userName">User Name:</label>
			<input type="text" name="userName" id="userName"
				spellcheck="false" autocomplete="off"
				required="required" placeholder="Enter user name" />
		</div>
		<c:if test="${fn:length(departments) > 0}">
			<div class="form-control">
				<label for="workDeptId">Select User's Department:</label>
				<select name="workDeptId" id="workDeptId" required="required">
					<option value="" selected disabled>
						-- Select Department --
					</option>
					<c:forEach items="${departments}" var="dept">
						<option value="${dept.getDeptId()}">
							${dept.getDeptName()}
						</option>
					</c:forEach>
				</select>
			</div>
		</c:if>
		<button type="submit">Save</button>
		&nbsp;&nbsp;
		<a href="${pageContext.request.contextPath}/tasks-app/users">Cancel</a>
	</form>
	<br>
</main>
</body>
</html>
