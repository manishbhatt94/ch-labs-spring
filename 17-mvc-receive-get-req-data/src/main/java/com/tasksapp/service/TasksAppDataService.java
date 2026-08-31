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
			boolean matched = workDeptId == null || (usr.getWorkDeptId() == workDeptId);
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

	public List<Project> filterProjects(String projNameSearchTerm) {
		String searchTerm = projNameSearchTerm.trim().toLowerCase();
		return projects.stream().filter((proj) -> {
			String fullProjName = proj.getProjName().toLowerCase();
			return fullProjName.contains(searchTerm);
		}).collect(Collectors.toList());
	}

	public void createProject(Project project) {
		projects.add(project);
	}

	private void seedInitialData() {

		Department kycTeam = new Department("KYC Tech");
		Department onboardingTeam = new Department("Onboarding Tech");
		Department discoveryTeam = new Department("Discovery Tech");

		departments.add(kycTeam);
		departments.add(onboardingTeam);
		departments.add(discoveryTeam);

		User aliceUser = new User("Alice Smith", kycTeam.getDeptId(), kycTeam.getDeptName());
		User amyUser = new User("Amy Keith", kycTeam.getDeptId(), kycTeam.getDeptName());
		User atkinsUser = new User("Atkins Jr. Smith", kycTeam.getDeptId(), kycTeam.getDeptName());

		User balakrishnanUser = new User("Rashmi Balakrishnan", onboardingTeam.getDeptId(),
				onboardingTeam.getDeptName());
		User billyUser = new User("Billy Jones", onboardingTeam.getDeptId(), onboardingTeam.getDeptName());
		User bhaskarUser = new User("Bhaskar Balaji", onboardingTeam.getDeptId(), onboardingTeam.getDeptName());

		User charuUser = new User("Charu Kumar", discoveryTeam.getDeptId(), discoveryTeam.getDeptName());
		User carlosUser = new User("Carlos Jon", discoveryTeam.getDeptId(), discoveryTeam.getDeptName());
		User camilaUser = new User("Camila H. Jonathan", discoveryTeam.getDeptId(), discoveryTeam.getDeptName());
		User chintuUser = new User("Chintu S. Kumaraswamy", discoveryTeam.getDeptId(), discoveryTeam.getDeptName());

		users.add(aliceUser);
		users.add(amyUser);
		users.add(atkinsUser);
		users.add(balakrishnanUser);
		users.add(billyUser);
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
				kycReadyProj.getProjName(), balakrishnanUser.getUserId(), balakrishnanUser.getUserName());

		tasks.add(regWithAadharApi);
		tasks.add(whitelistAadharServerIps);
		tasks.add(profileUsersPanNumber);

	}

}
