<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
	<meta charset="UTF-8">
	<title>StudyPlanner - All Courses</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/style.css"/>
</head>
<body>
<main>

	<h1>All Courses</h1>
	<ul>
		<c:forEach items="${courses}" var="course">
			<li>
				<a href="${pageContext.request.contextPath}/courses/${course.id}">
					${course.name}
				</a>
				— ${course.description}
			</li>
		</c:forEach>
	</ul>
	<br/>
	<a href="${pageContext.request.contextPath}/courses/new">+ Add New Course</a>
	&nbsp;|&nbsp;
	<a href="${pageContext.request.contextPath}/">← Home</a>

</main>
</body>
</html>
