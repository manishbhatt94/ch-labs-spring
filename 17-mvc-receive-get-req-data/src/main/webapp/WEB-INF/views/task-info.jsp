<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!DOCTYPE html>
<html lang="en">
<head>
	<meta charset="UTF-8">
	<title>TasksApp - Task Details</title>
	<link rel="stylesheet" href="${pageContext.request.contextPath}/static/style.css"/>
</head>
<body>
<main>
	<h1>TasksApp - Task Details</h1>

	<aside>
		<nav>
			<ul>
				<li><a href="${pageContext.request.contextPath}/tasks-app/">Home</a></li>
				<li>
					<a href="${pageContext.request.contextPath}/tasks-app/tasks">
						Tasks Listing
					</a>
				</li>
			</ul>
		</nav>
	</aside>

	<table border="1" class="xl">
		<caption>Task Details</caption>
		<c:choose>
			<c:when test="${task == null}">
				<tbody>
					<tr align="center">
						<td>Task with <code>taskId = ${taskId}</code> was <strong>NOT FOUND</strong>!</td>
					</tr>
				</tbody>
			</c:when>
			<c:otherwise>
				<thead>
					<tr>
						<th>taskId</th>
						<th>taskName</th>
						<th>linkedProjId</th>
						<th>linkedProjName</th>
						<th>assigneeId</th>
						<th>assigneeName</th>
					</tr>
				</thead>
				<tbody>
					<tr>
						<td>${task.getTaskId()}</td>
						<td>${task.getTaskName()}</td>
						<td>${task.getLinkedProjId()}</td>
						<td>${task.getLinkedProjName()}</td>
						<td>${task.getAssigneeId()}</td>
						<td>${task.getAssigneeName()}</td>
					</tr>
				</tbody>
			</c:otherwise>
		</c:choose>
	</table>
</main>
</body>
</html>
