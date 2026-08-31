<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"	 %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!DOCTYPE html>
<html lang="en">
<head>
	<meta charset="UTF-8">
	<title>TasksApp - Departments Listing</title>
	<link rel="stylesheet" href="${pageContext.request.contextPath}/static/style.css"/>
</head>
<body>
<main>
	<h1>TasksApp - Departments Listing</h1>

	<aside>
		<nav>
			<ul>
				<li><a href="${pageContext.request.contextPath}/tasks-app/">Home</a></li>
				<li>
					<a href="${pageContext.request.contextPath}/tasks-app/create-department">
						Add Department
					</a>
				</li>
			</ul>
		</nav>
	</aside>

	<h3>Filter Departments</h3>
	<form action="${pageContext.request.contextPath}/tasks-app/filter-departments" method="get">
		<label for="fltrDeptName">Filter by Department Name:</label>
		<input type="search" name="qDeptName" id="fltrDeptName"
			required="required" placeholder="Partial dept name"
			value="${searchedDeptName}" />
		<button type="submit">Search</button>
	</form>
	<br>

	<c:if test="${searchResultsPage == false}">
		<div class="heading-link">
			<h2>All Departments</h2>
		</div>
	</c:if>
	<c:if test="${searchResultsPage == true}">
		<div class="heading-link">
			<h2>Departments - Search Results</h2>
			<a href="${pageContext.request.contextPath}/tasks-app/departments">
				← Back to All Departments
			</a>
		</div>
	</c:if>

	<%@ include file="../partials/table-departments.jsp" %>
</main>
</body>
</html>
