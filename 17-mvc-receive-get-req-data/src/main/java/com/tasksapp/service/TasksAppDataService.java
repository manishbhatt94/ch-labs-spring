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

	private void seedInitialData() {

		Department kycTeam = new Department("KYC Tech");
		Department onboardingTeam = new Department("Onboarding Tech");
		Department discoveryTeam = new Department("Discovery Tech");

		departments.add(kycTeam);
		departments.add(onboardingTeam);
		departments.add(discoveryTeam);

		User aliceUser = new User("Alice", kycTeam.getDeptId(), kycTeam.getDeptName());
		User amyUser = new User("Amy", kycTeam.getDeptId(), kycTeam.getDeptName());
		User atkinsUser = new User("Atkins", kycTeam.getDeptId(), kycTeam.getDeptName());

		User balakrishnanUser = new User("Balakrishnan", onboardingTeam.getDeptId(), onboardingTeam.getDeptName());
		User billyUser = new User("Billy", onboardingTeam.getDeptId(), onboardingTeam.getDeptName());
		User bhaskarUser = new User("Bhaskar", onboardingTeam.getDeptId(), onboardingTeam.getDeptName());

		User charuUser = new User("Charu", discoveryTeam.getDeptId(), discoveryTeam.getDeptName());
		User carlosUser = new User("Carlos", discoveryTeam.getDeptId(), discoveryTeam.getDeptName());
		User camilaUser = new User("Camila", discoveryTeam.getDeptId(), discoveryTeam.getDeptName());
		User chintuUser = new User("Chintu", discoveryTeam.getDeptId(), discoveryTeam.getDeptName());

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
