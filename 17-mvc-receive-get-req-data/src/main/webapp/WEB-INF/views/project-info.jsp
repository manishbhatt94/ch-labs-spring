<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"	 %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!DOCTYPE html>
<html lang="en">
<head>
	<meta charset="UTF-8">
	<title>TasksApp - Project Details</title>
	<link rel="stylesheet" href="${pageContext.request.contextPath}/static/style.css"/>
</head>
<body>
<main>
	<h1>TasksApp - Project Details</h1>
	
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

	<c:if test="${not empty redirectMessage}">
		<div class="alert alert-success">${redirectMessage}</div>
	</c:if>

	<table border="1" class="sm">
		<caption>Project Details</caption>
		<c:choose>
			<c:when test="${project == null}">
				<tbody>
					<tr align="center">
						<td>Project with <code>projId = ${projId}</code> was <strong>NOT FOUND</strong>!</td>
					</tr>
				</tbody>
			</c:when>
			<c:otherwise>
				<thead>
					<tr><th>projId</th><th>projName</th></tr>
				</thead>
				<tbody>
					<tr>
						<td>${project.getProjId()}</td>
						<td>${project.getProjName()}</td>
					</tr>
				</tbody>
			</c:otherwise>
		</c:choose>
	</table>
</main>
</body>
</html>
