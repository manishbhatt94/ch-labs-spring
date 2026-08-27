<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
	<meta charset="UTF-8">
	<title>BookShelf - All Books</title>
	<link rel="stylesheet" href="${pageContext.request.contextPath}/static/style.css" />
</head>
<body>
<main>

	<h1>All Books</h1>
	<ul>
		<%--
			<c:forEach> — JSTL core tag. Replaces JSP scriptlet loop.
			items="${books}" reads the "books" attribute added by
			BookController via model.addAttribute("books", ...).
			var="book" is the loop variable — accessible as ${book.title} etc.
		--%>
		<c:forEach items="${books}" var="book">
			<li>
				<a href="${pageContext.request.contextPath}/books/${book.id}">
					${book.title} — ${book.author}
				</a>
			</li>
		</c:forEach>
	</ul>

	<br/>
	<a href="${pageContext.request.contextPath}/">← Back to Home</a>


</main>
</body>
</html>
