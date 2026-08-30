<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"	 %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

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
