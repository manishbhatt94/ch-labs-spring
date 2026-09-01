package com.tasksapp.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Service;

import com.tasksapp.model.Department;
import com.tasksapp.model.Project;
import com.tasksapp.model.Task;
import com.tasksapp.model.User;

@Service
public class TasksAppDataService {

	private List<Department> departments = new ArrayList<>();

	private List<User> users = new ArrayList<>();

	private List<Project> projects = new ArrayList<>();

	private List<Task> tasks = new ArrayList<>();

	@PostConstruct
	public void initService() {
		seedInitialData();
	}

	public List<Department> getDepartments() {
		return departments;
	}

	public List<User> getUsers() {
		return users;
	}

	public List<Project> getProjects() {
		return projects;
	}

	public List<Task> getTasks() {
		return tasks;
	}

	public Department getDepartmentById(int deptId) {
		return departments.stream().filter(dept -> deptId == dept.getDeptId()).findFirst().orElse(null);
	}

	public List<Department> filterDepartments(String deptNameSearchTerm) {
		String searchTerm = deptNameSearchTerm.trim().toLowerCase();
		return departments.stream().filter((dept) -> {
			String fullDeptName = dept.getDeptName().toLowerCase();
			return fullDeptName.contains(searchTerm);
		}).collect(Collectors.toList());
	}

	public Department createDepartment(String deptName) {
		Department department = new Department(deptName);
		departments.add(department);
		return department;
	}

	public User getUserById(int userId) {
		return users.stream().filter(usr -> userId == usr.getUserId()).findFirst().orElse(null);
	}

	public List<User> filterUsers(String partialUserName, Integer workDeptId) {
		String searchTerm = partialUserName.trim().toLowerCase();
		return users.stream().filter((usr) -> {
			String fullUserName = usr.getUserName().toLowerCase();
			boolean matched = workDeptId == null || (usr.getWorkDeptId() == workDeptId.intValue());
			matched = matched && (fullUserName.contains(searchTerm));
			return matched;
		}).collect(Collectors.toList());
	}

	public User createUser(String userName, int workDeptId, String workDeptName) {
		User user = new User(userName, workDeptId, workDeptName);
		users.add(user);
		return user;
	}

	public Project getProjectById(int projId) {
		return projects.stream().filter(proj -> projId == proj.getProjId()).findFirst().orElse(null);
	}

	public List<Project> filterProjects(String partialProjName) {
		String searchTerm = partialProjName.trim().toLowerCase();
		return projects.stream().filter((proj) -> {
			String fullProjName = proj.getProjName().toLowerCase();
			return fullProjName.contains(searchTerm);
		}).collect(Collectors.toList());
	}

	public Project createProject(Project project) {
		projects.add(project);
		return project;
	}

	public Task getTaskById(int taskId) {
		return tasks.stream().filter(task -> taskId == task.getTaskId()).findFirst().orElse(null);
	}

	public List<Task> filterTasks(String partialTaskName, Integer linkedProjId, Integer assigneeId) {
		String searchTerm = partialTaskName.trim().toLowerCase();
		return tasks.stream().filter((task) -> {
			String fullTaskName = task.getTaskName().toLowerCase();
			boolean matched = true;
			matched = matched && (linkedProjId == null || (task.getLinkedProjId() == linkedProjId.intValue()));
			matched = matched && (assigneeId == null || (task.getAssigneeId() == assigneeId.intValue()));
			matched = matched && (fullTaskName.contains(searchTerm));
			return matched;
		}).collect(Collectors.toList());
	}

	public Task createTask(Task task) {
		tasks.add(task);
		return task;
	}

	private void seedInitialData() {

		Department kycTeam = new Department("KYC Tech");
		Department onboardingTeam = new Department("Onboarding Tech");
		Department discoveryTeam = new Department("Discovery Tech");

		departments.add(kycTeam);
		departments.add(onboardingTeam);
		departments.add(discoveryTeam);

		User aliceUser = new User("Uday Shetty", kycTeam.getDeptId(), kycTeam.getDeptName());
		User amyUser = new User("Amy Keith", kycTeam.getDeptId(), kycTeam.getDeptName());
		User spoorthyUser = new User("Spoorthy Udayakumar Kulkarni", kycTeam.getDeptId(), kycTeam.getDeptName());
		User atkinsUser = new User("Vidya Gowda", kycTeam.getDeptId(), kycTeam.getDeptName());

		User rashmiUser = new User("Rashmi Balakrishnan", onboardingTeam.getDeptId(), onboardingTeam.getDeptName());
		User billyUser = new User("Bhimarao Shetty", onboardingTeam.getDeptId(), onboardingTeam.getDeptName());
		User kuldeepUser = new User("Kuldeep Melligeri", onboardingTeam.getDeptId(), onboardingTeam.getDeptName());
		User bhaskarUser = new User("Bhaskar Balaji", onboardingTeam.getDeptId(), onboardingTeam.getDeptName());

		User charuUser = new User("Charu Kumar", discoveryTeam.getDeptId(), discoveryTeam.getDeptName());
		User carlosUser = new User("Carlos Jon", discoveryTeam.getDeptId(), discoveryTeam.getDeptName());
		User camilaUser = new User("Camila H. Jonathan", discoveryTeam.getDeptId(), discoveryTeam.getDeptName());
		User chintuUser = new User("Chintu S. Kumaraswamy", discoveryTeam.getDeptId(), discoveryTeam.getDeptName());

		users.add(aliceUser);
		users.add(amyUser);
		users.add(spoorthyUser);
		users.add(atkinsUser);
		users.add(rashmiUser);
		users.add(billyUser);
		users.add(kuldeepUser);
		users.add(bhaskarUser);
		users.add(charuUser);
		users.add(carlosUser);
		users.add(camilaUser);
		users.add(chintuUser);

		Project aadhaarProj = new Project("Aadhaar Integration Module");
		Project kycReadyProj = new Project("KYC Readiness Tracker");
		Project sponsoredDiscoveryProj = new Project("Sponsored Partners Discovery");

		projects.add(aadhaarProj);
		projects.add(kycReadyProj);
		projects.add(sponsoredDiscoveryProj);

		Task regWithAadharApi = new Task("Register application at Aadhaar Developer Console", aadhaarProj.getProjId(),
				aadhaarProj.getProjName(), aliceUser.getUserId(), aliceUser.getUserName());
		Task whitelistAadharServerIps = new Task("Whitelist Aadhaar Server IPs for web-hooks", aadhaarProj.getProjId(),
				aadhaarProj.getProjName(), amyUser.getUserId(), amyUser.getUserName());
		Task profileUsersPanNumber = new Task("Profile User's PAN Number", kycReadyProj.getProjId(),
				kycReadyProj.getProjName(), rashmiUser.getUserId(), rashmiUser.getUserName());

		tasks.add(regWithAadharApi);
		tasks.add(whitelistAadharServerIps);
		tasks.add(profileUsersPanNumber);

	}

}
