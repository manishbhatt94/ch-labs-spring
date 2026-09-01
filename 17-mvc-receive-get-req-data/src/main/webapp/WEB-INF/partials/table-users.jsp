<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"	 %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<table border="1" class="lg">
	<caption>List of Users</caption>
	<c:choose>
		<c:when test="${users == null}">
			<tbody>
				<tr align="center"><td>List of users is <code>null</code></td></tr>
			</tbody>
		</c:when>
		<c:when test="${fn:length(users) == 0}">
			<tbody>
				<tr align="center"><td>List of users is <strong>empty</strong></td></tr>
			</tbody>
		</c:when>
		<c:otherwise>
			<thead>
				<tr>
					<th>userId</th>
					<th>userName</th>
					<th>workDeptId</th>
					<th>workDeptName</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${users}" var="data">
					<tr>
						<td>${data.getUserId()}</td>
						<td>
							<a href="${pageContext.request.contextPath}/tasks-app/users/${data.getUserId()}">
								${data.getUserName()}
							</a>
						</td>
						<td>${data.getWorkDeptId()}</td>
						<td>${data.getWorkDeptName()}</td>
					</tr>
				</c:forEach>
			</tbody>
		</c:otherwise>
	</c:choose>
</table>
