<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
	<meta charset="UTF-8">
	<title>StudyPlanner - Add Course</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/style.css"/>
</head>
<body>
<main>

	<h1>Add New Course</h1>

	<!--
		Plain HTML form — action posts to /courses, method POST.
		Input field names ("name", "description") must exactly match
		the setter names in Course.java (setName, setDescription → "name", "description").
		Spring's @ModelAttribute uses these names to call the right setters
		during form binding — if names don't match, fields arrive as null.
	-->
	<form action="${pageContext.request.contextPath}/courses" method="post">
		<div>
			<label for="name">Course Name:</label><br/>
			<input type="text" id="name" name="name" required/>
		</div>
		<br/>
		<div>
			<label for="description">Description:</label><br/>
			<textarea id="description" name="description" rows="3" cols="40"></textarea>
		</div>
		<br/>
		<button type="submit">Add Course</button>
		&nbsp;
		<a href="${pageContext.request.contextPath}/courses">Cancel</a>
	</form>

</main>
</body>
</html>
