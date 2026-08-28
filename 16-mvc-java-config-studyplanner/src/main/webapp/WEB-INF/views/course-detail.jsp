<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
	<meta charset="UTF-8">
	<title>StudyPlanner - Course Details</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/style.css"/>
</head>
<body>
<main>

	<div class="course-detail">
		<c:choose>
			<c:when test="${course != null}">
				<h1>${course.name}</h1>
				<p>${course.description}</p>
			</c:when>
			<c:otherwise>
				<h1>Course Not Found</h1>
				<p>No course exists with that ID.</p>
			</c:otherwise>
		</c:choose>
	</div>

	<br/>
	<a href="${pageContext.request.contextPath}/courses">← All Courses</a>

</main>
</body>
</html>
