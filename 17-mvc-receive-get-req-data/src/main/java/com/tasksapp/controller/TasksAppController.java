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

import com.tasksapp.model.Department;
import com.tasksapp.model.Project;
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
	public String createUser(@RequestParam String userName, @RequestParam Integer workDeptId) {
		Department dept = service.getDepartmentById(workDeptId);
		User user = service.createUser(userName, dept.getDeptId(), dept.getDeptName());
		return "redirect:/tasks-app/users/" + user.getUserId();
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
	public String createProject(@ModelAttribute Project project) {
		// Note: Used @ModelAttribute here to inject the entire form data into an
		// object, instead of manually picking each form field, and then constructing
		// the object ourselves.
		service.createProject(project);
		return "redirect:/tasks-app/projects/" + project.getProjId();
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

}
