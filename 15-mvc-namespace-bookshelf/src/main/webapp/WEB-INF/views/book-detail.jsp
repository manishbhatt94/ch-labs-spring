<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
	<meta charset="UTF-8">
	<title>BookShelf - Book Details</title>
	<link rel="stylesheet" href="${pageContext.request.contextPath}/static/style.css" />
</head>
<body>
<main>


	<div class="book-detail">
		<%--
			<c:choose> + <c:when> + <c:otherwise> — JSTL equivalent of if/else.
			Guards against null book (e.g. /books/99 where id doesn't exist).
		--%>
		<c:choose>
			<c:when test="${book != null}">
				<h1>${book.title}</h1>
				<p><strong>Author:</strong> ${book.author}</p>
				<p><strong>ID:</strong> ${book.id}</p>
			</c:when>
			<c:otherwise>
				<h1>Book Not Found</h1>
				<p>No book exists with <strong>ID:</strong> ${requestedBookId}.</p>
			</c:otherwise>
		</c:choose>
	</div>

	<br/>
	<a href="${pageContext.request.contextPath}/books">← Back to All Books</a>


</main>
</body>
</html>
