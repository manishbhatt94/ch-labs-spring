<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" isELIgnored="false" %>
<%@ page import="java.util.List" %>
<!DOCTYPE html>
<html lang="en">
<head>
	<title>News Articles</title>
</head>
<body>
	<header>
		<nav>
			<ul>
				<li><a href="..">Home</a></li>
			</ul>
		</nav>
	</header>

    <h1>News Articles</h1>
    <%
        List<String> articles = (List<String>) request.getAttribute("articles");
        if (articles != null) {
            for (String article : articles) {
                out.println("<p>" + article + "</p>");
            }
        }
    %>
    <a href="../admin/dashboard">Go to Admin</a>
</body>
</html>
