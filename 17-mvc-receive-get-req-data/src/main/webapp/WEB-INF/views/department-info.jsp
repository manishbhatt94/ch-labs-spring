<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"	 %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!DOCTYPE html>
<html lang="en">
<head>
	<meta charset="UTF-8">
	<title>TasksApp - Department Details</title>
	<link rel="stylesheet" href="${pageContext.request.contextPath}/static/style.css"/>
</head>
<body>
<main>
	<h1>TasksApp - Department Details</h1>
	
	<aside>
		<nav>
			<ul>
				<li><a href="${pageContext.request.contextPath}/tasks-app/">Home</a></li>
				<li><a href="${pageContext.request.contextPath}/tasks-app/departments">Departments Listing</a></li>
			</ul>
		</nav>
	</aside>

	<table border="1" class="sm">
		<caption>Department Details</caption>
		<c:choose>
			<c:when test="${department == null}">
				<tbody>
					<tr align="center">
						<td>Department with <code>deptId = ${deptId}</code> was <strong>NOT FOUND</strong>!</td>
					</tr>
				</tbody>
			</c:when>
			<c:otherwise>
				<thead>
					<tr><th>deptId</th><th>deptName</th></tr>
				</thead>
				<tbody>
					<tr>
						<td>${department.getDeptId()}</td>
						<td>${department.getDeptName()}</td>
					</tr>
				</tbody>
			</c:otherwise>
		</c:choose>
	</table>
</main>
</body>
</html>
