<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"	 %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!DOCTYPE html>
<html lang="en">
<head>
	<meta charset="UTF-8">
	<title>TasksApp - Add Task</title>
	<link rel="stylesheet" href="${pageContext.request.contextPath}/static/style.css"/>
</head>
<body>
<main>
	<h1>TasksApp - Add Task</h1>

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

	<h2>Create a new task</h2>
	<form action="${pageContext.request.contextPath}/tasks-app/tasks" method="post">
		<div class="form-control">
			<label for="taskName">Task Name:</label>
			<input type="text" name="taskName" id="taskName"
				spellcheck="false" autocomplete="off"
				required="required" placeholder="Enter task name" />
		</div>
		<c:if test="${fn:length(projects) > 0}">
			<div class="form-control">
				<label for="linkedProjId">Select Task's Project:</label>
				<select name="linkedProjId" id="linkedProjId" required="required">
					<option value="" selected disabled>
						-- Select Linked Project --
					</option>
					<c:forEach items="${projects}" var="proj">
						<option value="${proj.getProjId()}">
							${proj.getProjName()}
						</option>
					</c:forEach>
				</select>
			</div>
		</c:if>
		<c:if test="${fn:length(users) > 0}">
			<div class="form-control">
				<label for="assigneeId">Select Task's Assignee:</label>
				<select name="assigneeId" id="assigneeId" required="required">
					<option value="" selected disabled>
						-- Select Assignee --
					</option>
					<c:forEach items="${users}" var="usr">
						<option value="${usr.getUserId()}">
							${usr.getUserName()}
						</option>
					</c:forEach>
				</select>
			</div>
		</c:if>
		<button type="submit">Save</button>
		&nbsp;&nbsp;
		<a href="${pageContext.request.contextPath}/tasks-app/tasks">Cancel</a>
	</form>
	<br>
</main>
</body>
</html>
