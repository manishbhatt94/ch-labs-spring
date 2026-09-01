<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"	 %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!DOCTYPE html>
<html lang="en">
<head>
	<meta charset="UTF-8">
	<title>TasksApp - Users Listing</title>
	<link rel="stylesheet" href="${pageContext.request.contextPath}/static/style.css"/>
</head>
<body>
<main>
	<h1>TasksApp - Users Listing</h1>

	<aside>
		<nav>
			<ul>
				<li><a href="${pageContext.request.contextPath}/tasks-app/">Home</a></li>
				<li>
					<a href="${pageContext.request.contextPath}/tasks-app/create-user">
						Add User
					</a>
				</li>
			</ul>
		</nav>
	</aside>

	<h3>Filter Users</h3>
	<form action="${pageContext.request.contextPath}/tasks-app/filter-users" method="get">
		<c:if test="${fn:length(departments) > 0}">
			<div class="form-control">
				<label for="fltrWorkDept">Filter by User's Department:</label>
				<select name="qWorkDeptId" id="fltrWorkDept">
					<option
						value=""
						<c:if test="${searchedDeptId == null}">selected</c:if>
					>
						-- Select Department --
					</option>
					<c:forEach items="${departments}" var="dept">
						<option
							value="${dept.getDeptId()}"
							<c:if test="${searchedDeptId == dept.getDeptId()}">selected</c:if>
						>
							${dept.getDeptName()}
						</option>
					</c:forEach>
				</select>
			</div>
		</c:if>
		<label for="fltrUserName">Filter by User Name:</label>
		<input type="search" name="qUserName" id="fltrUserName"
			required="required" placeholder="Partial user name"
			value="${searchedUserName}" />
		<button type="submit">Search</button>
	</form>

	<c:if test="${searchResultsPage == false}">
		<div class="heading-link">
			<h2>All Users</h2>
		</div>
	</c:if>
	<c:if test="${searchResultsPage == true}">
		<div class="heading-link">
			<h2>Users - Search Results</h2>
			<a href="${pageContext.request.contextPath}/tasks-app/users">
				← Back to All Users
			</a>
		</div>
	</c:if>

	<%@ include file="../partials/table-users.jsp" %>
</main>
</body>
</html>
