<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"	 %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!DOCTYPE html>
<html lang="en">
<head>
	<meta charset="UTF-8">
	<title>TasksApp - Projects Listing</title>
	<link rel="stylesheet" href="${pageContext.request.contextPath}/static/style.css"/>
</head>
<body>
<main>
	<h1>TasksApp - Projects Listing</h1>

	<aside>
		<nav>
			<ul>
				<li><a href="${pageContext.request.contextPath}/tasks-app/">Home</a></li>
				<li>
					<a href="${pageContext.request.contextPath}/tasks-app/create-project">
						Add Project
					</a>
				</li>
			</ul>
		</nav>
	</aside>

	<h3>Filter Projects</h3>
	<form action="${pageContext.request.contextPath}/tasks-app/filter-projects" method="get">
		<label for="fltrProjName">Filter by Project Name:</label>
		<input type="search" name="qProjName" id="fltrProjName"
			required="required" placeholder="Partial project name"
			spellcheck="false" autocomplete="off"
			value="${searchedProjName}" />
		<button type="submit">Search</button>
	</form>
	<br>

	<c:if test="${searchResultsPage == false}">
		<div class="heading-link">
			<h2>All Projects</h2>
		</div>
	</c:if>
	<c:if test="${searchResultsPage == true}">
		<div class="heading-link">
			<h2>Projects - Search Results</h2>
			<a href="${pageContext.request.contextPath}/tasks-app/projects">
				← Back to All Projects
			</a>
		</div>
	</c:if>

	<%@ include file="../partials/table-projects.jsp" %>
</main>
</body>
</html>
