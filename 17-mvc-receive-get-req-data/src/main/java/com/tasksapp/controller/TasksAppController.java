package com.tasksapp.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

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

	@GetMapping("/filter-departments")
	public String departmentsFiltered(Model model, HttpServletRequest req) {
		String queriedDeptName = req.getParameter("qDeptName");
		model.addAttribute("departments", service.filterDepartments(queriedDeptName));
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

}
