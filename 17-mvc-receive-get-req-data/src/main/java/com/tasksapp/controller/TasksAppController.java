package com.tasksapp.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.tasksapp.dto.TaskFilter;
import com.tasksapp.model.Department;
import com.tasksapp.model.Project;
import com.tasksapp.model.Task;
import com.tasksapp.model.User;
import com.tasksapp.service.TasksAppDataService;

@Controller
@RequestMapping("/tasks-app")
public class TasksAppController {

	@Autowired
	private TasksAppDataService service;

	@GetMapping("/departments")
	public String departmentsListing(Model model) {
		model.addAttribute("departments", service.getDepartments());
		model.addAttribute("searchResultsPage", false);
		model.addAttribute("searchedDeptName", "");
		return "departments-list";
	}

	@PostMapping("/departments")
	public String createDepartment(HttpServletRequest req) {
		String deptName = req.getParameter("deptName");
		Department department = service.createDepartment(deptName);

		// Note: We could have used RedirectAttributes in this controller method as
		// well, but not using it here, just to demonstrate that we can also manually
		// construct the redirect URL with the deptId of the newly created department.

		return "redirect:/tasks-app/departments/" + department.getDeptId();
	}

	@GetMapping("/filter-departments")
	public String departmentsFiltered(Model model, HttpServletRequest req) {
		String queriedDeptName = req.getParameter("qDeptName");
		List<Department> filteredDepartments = service.filterDepartments(queriedDeptName);

		model.addAttribute("departments", filteredDepartments);
		model.addAttribute("searchResultsPage", true);
		model.addAttribute("searchedDeptName", queriedDeptName);

		return "departments-list";
	}

	@GetMapping("/departments/{deptId}")
	public String departmentDetails(Model model, @PathVariable int deptId) {
		model.addAttribute("department", service.getDepartmentById(deptId));
		model.addAttribute("deptId", deptId);
		return "department-info";
	}

	@GetMapping("/users")
	public String usersListing(Model model) {
		List<Department> departments = service.getDepartments();
		List<User> users = service.getUsers();

		model.addAttribute("departments", departments);
		model.addAttribute("users", users);
		model.addAttribute("searchResultsPage", false);
		model.addAttribute("searchedUserName", "");
		model.addAttribute("searchedDeptId", null);

		return "users-list";
	}

	@GetMapping("/create-user")
	public String createUserForm(Model model) {
		List<Department> departments = service.getDepartments();
		model.addAttribute("departments", departments);

		return "user-create-form";
	}

	@PostMapping("/users")
	public String createUser(@RequestParam String userName, @RequestParam Integer workDeptId,
			RedirectAttributes redirectAttrs) {

		Department dept = service.getDepartmentById(workDeptId);
		User user = service.createUser(userName, dept.getDeptId(), dept.getDeptName());

		// Manually construct the redirect URL with the userId of the newly created
		// user:
		// (This faces the issue of also manually calling toString() if applicable, and
		// URL-encoding the value of userId).
//		return "redirect:/tasks-app/users/" + user.getUserId();

		// Below we use RedirectAttributes interface, which offers a safer/cleaner
		// alternative to manually string-concatenating the redirect URL:
		redirectAttrs.addAttribute("userId", user.getUserId()); // -> goes into the URL.

		// Note: The above line redirectAttrs.addAttribute("userId", user.getUserId());
		// will automatically URL-encode the value of user.getUserId(),
		// and replace it in the redirect URL template string
		// "redirect:/tasks-app/users/{userId}" where {userId} is a placeholder.
		// If the redirect URL template string does not contain a placeholder for the
		// attribute, it will be appended as a query parameter instead,
		// like "redirect:/tasks-app/users?userId=123". But in our case, we have a
		// placeholder {userId} in the redirect URL template string, so it will be
		// replaced with the actual value of user.getUserId().

		// Add a flash attribute to pass a one-time message to the redirected page:
		redirectAttrs.addFlashAttribute("redirectMessage", "User created successfully!"); // -> one-time, invisible

		return "redirect:/tasks-app/users/{userId}";
	}

	@GetMapping("/filter-users")
	public String usersFiltered(Model model, @RequestParam("qUserName") String partialUserName,
			@RequestParam(name = "qWorkDeptId", required = false) Integer workDeptId) {

		System.out.println("[usersFiltered] partialUserName: " + partialUserName);

		System.out.println("[usersFiltered] workDeptId: " + workDeptId);
		// ↑↑ Prints: null; when the select HTML form element with name "qWorkDeptId"
		// is kept at <option> having value as "" (Check the HTML Code).

		List<Department> departments = service.getDepartments();
		List<User> filteredUsers = service.filterUsers(partialUserName, workDeptId);

		model.addAttribute("departments", departments);
		model.addAttribute("users", filteredUsers);
		model.addAttribute("searchResultsPage", true);
		model.addAttribute("searchedUserName", partialUserName);
		model.addAttribute("searchedDeptId", workDeptId);

		return "users-list";
	}

	@GetMapping("/users/{userId}")
	public String userDetails(Model model, @PathVariable int userId) {
		model.addAttribute("user", service.getUserById(userId));
		model.addAttribute("userId", userId);
		return "user-info";
	}

	@GetMapping("/projects")
	public String projectsListing(Model model) {
		model.addAttribute("projects", service.getProjects());
		model.addAttribute("searchResultsPage", false);
		model.addAttribute("searchedProjName", "");
		return "projects-list";
	}

	@PostMapping("/projects")
	public String createProject(@ModelAttribute Project project, RedirectAttributes redirectAttrs) {
		// Note: Used @ModelAttribute here to inject the entire form data into an
		// object, instead of manually picking each form field, and then constructing
		// the object ourselves.
		service.createProject(project);

//		return "redirect:/tasks-app/projects/" + project.getProjId();

		// Just demonstrating that below methods can be chained together, as they return
		// the same RedirectAttributes object.
		// @formatter:off
		redirectAttrs
				.addAttribute("projId", project.getProjId())
				.addFlashAttribute("redirectMessage", "Project created successfully!");
		// @formatter:on

		return "redirect:/tasks-app/projects/{projId}";
	}

	@GetMapping("/filter-projects")
	public String projectFiltered(Model model, @RequestParam("qProjName") String partialProjName) {
		List<Project> filteredProjects = service.filterProjects(partialProjName);

		model.addAttribute("projects", filteredProjects);
		model.addAttribute("searchResultsPage", true);
		model.addAttribute("searchedProjName", partialProjName);

		return "projects-list";
	}

	@GetMapping("/projects/{projId}")
	public String projectDetails(Model model, @PathVariable int projId) {
		model.addAttribute("project", service.getProjectById(projId));
		model.addAttribute("projId", projId);
		return "project-info";
	}

	@GetMapping("/tasks")
	public String tasksListing(Model model) {
		List<Project> projects = service.getProjects();
		List<User> users = service.getUsers();
		List<Task> tasks = service.getTasks();

		model.addAttribute("projects", projects);
		model.addAttribute("users", users);
		model.addAttribute("tasks", tasks);
		model.addAttribute("searchResultsPage", false);
		model.addAttribute("searchedTaskName", "");
		model.addAttribute("searchedProjId", null);
		model.addAttribute("searchedAssigneeId", null);

		return "tasks-list";
	}

	@GetMapping("/create-task")
	public String createTaskForm(Model model) {
		List<Project> projects = service.getProjects();
		List<User> users = service.getUsers();

		model.addAttribute("projects", projects);
		model.addAttribute("users", users);

		return "task-create-form";
	}

	@PostMapping("/tasks")
	public String createTask(@ModelAttribute Task task, RedirectAttributes redirectAttrs) {
		System.out.println("[createTask] task: " + task);
		// Note: Used @ModelAttribute here to inject the entire form data into an
		// object, instead of manually picking each form field, and then constructing
		// the object ourselves.

		// Populate the linkedProjName and assigneeName fields in the task object:
		Project linkedProject = service.getProjectById(task.getLinkedProjId());
		User assignee = service.getUserById(task.getAssigneeId());
		if (linkedProject != null) {
			task.setLinkedProjName(linkedProject.getProjName());
		}
		if (assignee != null) {
			task.setAssigneeName(assignee.getUserName());
		}
		System.out.println("[createTask] task after populating linkedProjName and assigneeName: " + task);

		// Save the task using the service:
		service.createTask(task);

		// Redirect to the task details page after creation:
//		return "redirect:/tasks-app/tasks/" + task.getTaskId();

		// Use RedirectAttributes to pass the taskId as a path variable in the redirect
		// URL:
		redirectAttrs.addAttribute("taskId", task.getTaskId()); // -> goes into the URL.

		// Add a flash attribute to pass a one-time message to the redirected page:
		redirectAttrs.addFlashAttribute("redirectMessage", "Task created successfully!"); // -> one-time, invisible

		return "redirect:/tasks-app/tasks/{taskId}";
	}

	@GetMapping("/filter-tasks")
	public String tasksFiltered(Model model, @ModelAttribute TaskFilter taskFilter) {

		System.out.println("[tasksFiltered] taskFilter: " + taskFilter);

		List<Project> projects = service.getProjects();
		List<User> users = service.getUsers();
		List<Task> filteredTasks = service.filterTasks(taskFilter.getTaskName(), taskFilter.getLinkedProjId(),
				taskFilter.getAssigneeId());

		model.addAttribute("projects", projects);
		model.addAttribute("users", users);
		model.addAttribute("tasks", filteredTasks);
		model.addAttribute("searchResultsPage", true);
		model.addAttribute("searchedTaskName", taskFilter.getTaskName());
		model.addAttribute("searchedProjId", taskFilter.getLinkedProjId());
		model.addAttribute("searchedAssigneeId", taskFilter.getAssigneeId());

		return "tasks-list";
	}

	@GetMapping("/tasks/{taskId}")
	public String taskDetails(Model model, @PathVariable int taskId) {
		model.addAttribute("task", service.getTaskById(taskId));
		model.addAttribute("taskId", taskId);
		return "task-info";
	}

}
