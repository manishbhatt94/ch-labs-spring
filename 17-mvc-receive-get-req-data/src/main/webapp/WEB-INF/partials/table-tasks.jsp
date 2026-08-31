<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"	 %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<table border="1" class="xl">
	<caption>List of Tasks</caption>
	<c:choose>
		<c:when test="${tasks == null}">
			<tbody>
				<tr align="center"><td>List of tasks is <code>null</code></td></tr>
			</tbody>
		</c:when>
		<c:when test="${fn:length(tasks) == 0}">
			<tbody>
				<tr align="center"><td>List of tasks is <strong>empty</strong></td></tr>
			</tbody>
		</c:when>
		<c:otherwise>
			<thead>
				<tr>
					<th>taskId</th>
					<th>taskName</th>
					<th>linkedProjId</th>
					<th>linkedProjName</th>
					<th>assigneeId</th>
					<th>assigneeName</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${tasks}" var="data">
					<tr>
						<td>${data.getTaskId()}</td>
						<td>
							<a href="${pageContext.request.contextPath}/tasks-app/tasks/${data.getTaskId()}">
								${data.getTaskName()}
							</a>
						</td>
						<td>${data.getLinkedProjId()}</td>
						<td>${data.getLinkedProjName()}</td>
						<td>${data.getAssigneeId()}</td>
						<td>${data.getAssigneeName()}</td>
					</tr>
				</c:forEach>
			</tbody>
		</c:otherwise>
	</c:choose>
</table>
