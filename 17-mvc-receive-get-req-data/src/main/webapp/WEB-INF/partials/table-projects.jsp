<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"	 %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<table border="1" class="sm">
	<caption>List of Projects</caption>
	<c:choose>
		<c:when test="${projects == null}">
			<tbody>
				<tr align="center"><td>List of projects is <code>null</code></td></tr>
			</tbody>
		</c:when>
		<c:when test="${fn:length(projects) == 0}">
			<tbody>
				<tr align="center"><td>List of projects is <strong>empty</strong></td></tr>
			</tbody>
		</c:when>
		<c:otherwise>
			<thead>
				<tr><th>projId</th><th>projName</th></tr>
			</thead>
			<tbody>
				<c:forEach items="${projects}" var="data">
					<tr>
						<td>${data.getProjId()}</td>
						<td>
							<a href="${pageContext.request.contextPath}/tasks-app/projects/${data.getProjId()}">
								${data.getProjName()}
							</a>
						</td>
					</tr>
				</c:forEach>
			</tbody>
		</c:otherwise>
	</c:choose>
</table>
