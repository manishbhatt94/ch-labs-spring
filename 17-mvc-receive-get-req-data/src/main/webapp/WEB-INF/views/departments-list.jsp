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
			</ul>
		</nav>
	</aside>
	
	<h3>Filter Departments</h3>
	<form action="${pageContext.request.contextPath}/tasks-app/filter-departments" method="get">
		<label for="fltrDeptName">Filter by Department Name: </label>
		<input type="search" name="filterDeptName" id="fltrDeptName"
			required="required" placeholder="Partial dept name" />
		<button type="submit">Search</button>
	</form>
	
	<h2>Departments</h2>

	<table border="1" class="sm">
		<caption>List of Departments</caption>
		<c:choose>
			<c:when test="${departments == null}">
				<tbody>
					<tr align="center"><td>List of departments is <code>null</code></td></tr>
				</tbody>
			</c:when>
			<c:when test="${fn:length(departments) == 0}">
				<tbody>
					<tr align="center"><td>List of departments is <strong>empty</strong></td></tr>
				</tbody>
			</c:when>
			<c:otherwise>
				<thead>
					<tr><th>deptId</th><th>deptName</th></tr>
				</thead>
				<tbody>
					<c:forEach items="${departments}" var="data">
						<tr>
							<td>${data.getDeptId()}</td>
							<td>
								<a href="${pageContext.request.contextPath}/tasks-app/departments/${data.getDeptId()}">
									${data.getDeptName()}
								</a>
							</td>
						</tr>
					</c:forEach>
				</tbody>
			</c:otherwise>
		</c:choose>
	</table>
</main>
</body>
</html>
