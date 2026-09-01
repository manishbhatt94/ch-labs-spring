<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"	 %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!DOCTYPE html>
<html lang="en">
<head>
	<meta charset="UTF-8">
	<title>TasksApp - Tasks Listing</title>
	<link rel="stylesheet" href="${pageContext.request.contextPath}/static/style.css"/>
</head>
<body>
<main>
	<h1>TasksApp - Tasks Listing</h1>

	<aside>
		<nav>
			<ul>
				<li><a href="${pageContext.request.contextPath}/tasks-app/">Home</a></li>
				<li>
					<a href="${pageContext.request.contextPath}/tasks-app/create-task">
						Add Task
					</a>
				</li>
			</ul>
		</nav>
	</aside>

	<h3>Filter Tasks</h3>
	<form action="${pageContext.request.contextPath}/tasks-app/filter-tasks" method="get"
			id="filter-tasks-form">
		<p style="color: dimgray; font-style: italic; font-size: 0.9em;">
			Fill in one or more of the following fields to filter tasks. Then click the "Search" button.
		</p>
		<c:if test="${fn:length(projects) > 0}">
			<div class="form-control">
				<label for="fltrLinkedProj">Filter by Task's Project:</label>
				<select name="linkedProjId" id="fltrLinkedProj">
					<option
						value=""
						<c:if test="${searchedProjId == null}">selected</c:if>
					>
						-- Select Linked Project --
					</option>
					<c:forEach items="${projects}" var="proj">
						<option
							value="${proj.getProjId()}"
							<c:if test="${searchedProjId == proj.getProjId()}">selected</c:if>
						>
							${proj.getProjName()}
						</option>
					</c:forEach>
				</select>
			</div>
		</c:if>
		<c:if test="${fn:length(users) > 0}">
			<div class="form-control">
				<label for="fltrAssignee">Filter by Task's Assignee:</label>
				<select name="assigneeId" id="fltrAssignee">
					<option
						value=""
						<c:if test="${searchedAssigneeId == null}">selected</c:if>
					>
						-- Select Assignee --
					</option>
					<c:forEach items="${users}" var="usr">
						<option
							value="${usr.getUserId()}"
							<c:if test="${searchedAssigneeId == usr.getUserId()}">selected</c:if>
						>
							${usr.getUserName()}
						</option>
					</c:forEach>
				</select>
			</div>
		</c:if>
		<div class="form-control">
			<label for="fltrTaskName">Filter by Task Name:</label>
			<input type="search" name="taskName" id="fltrTaskName"
				placeholder="Partial task name" value="${searchedTaskName}" />
		</div>
		<button type="submit">Search</button>
	</form>
	<br>

	<c:if test="${searchResultsPage == false}">
		<div class="heading-link">
			<h2>All Tasks</h2>
		</div>
	</c:if>
	<c:if test="${searchResultsPage == true}">
		<div class="heading-link">
			<h2>Tasks - Search Results</h2>
			<a href="${pageContext.request.contextPath}/tasks-app/tasks">
				← Back to All Tasks
			</a>
		</div>
	</c:if>

	<%@ include file="../partials/table-tasks.jsp" %>
</main>


<script type="text/javascript">
filterFormHandling();

function filterFormHandling() {
  const filterForm = document.forms["filter-tasks-form"];
  if (!filterForm) {
    return;
  }
  const submitBtn = filterForm.querySelector('[type="submit"]');
  if (!submitBtn) {
    return;
  }

  const fields = Array.prototype.slice.call(filterForm.elements)
    .filter((el) => {
      return el.type !== "submit" && el.nodeName !== "BUTTON";
    });

  fields.forEach(f => f.addEventListener("input", handleInput));

  checkInitialState();

  function handleInput() {
    const valid = checkValidity();
    if (valid) {
      submitBtn.disabled = "";
    } else {
      submitBtn.disabled = "disabled";
    }
  }

  function checkInitialState() {
    const valid = checkValidity();
    if (valid) {
      submitBtn.disabled = "";
    } else {
      submitBtn.disabled = "disabled";
    }
  }

  function checkValidity() {
    const valid = fields.some((el) => {
      return el.value.trim() !== "";
    });
    return valid;
  }
}
</script>


</body>
</html>
