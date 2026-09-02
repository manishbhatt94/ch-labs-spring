<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!DOCTYPE html>
<html lang="en">
<head>
	<meta charset="UTF-8">
	<title>TasksApp - User Details</title>
	<link rel="stylesheet" href="${pageContext.request.contextPath}/static/style.css"/>
</head>
<body>
<main>
	<h1>TasksApp - User Details</h1>

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

	<c:if test="${not empty redirectMessage}">
		<div class="alert alert-success">${redirectMessage}</div>
	</c:if>

	<table border="1" class="lg">
		<caption>User Details</caption>
		<c:choose>
			<c:when test="${user == null}">
				<tbody>
					<tr align="center">
						<td>User with <code>userId = ${userId}</code> was <strong>NOT FOUND</strong>!</td>
					</tr>
				</tbody>
			</c:when>
			<c:otherwise>
				<thead>
					<tr>
						<th>userId</th>
						<th>userName</th>
						<th>workDeptId</th>
						<th>workDeptName</th>
					</tr>
				</thead>
				<tbody>
					<tr>
						<td>${user.getUserId()}</td>
						<td>${user.getUserName()}</td>
						<td>${user.getWorkDeptId()}</td>
						<td>${user.getWorkDeptName()}</td>
					</tr>
				</tbody>
			</c:otherwise>
		</c:choose>
	</table>
</main>
</body>
</html>
