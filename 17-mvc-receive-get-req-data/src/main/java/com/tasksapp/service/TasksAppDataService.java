package com.tasksapp.service;

import java.util.ArrayList;
import java.util.List;

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

	private void seedInitialData() {

		Department kycTeam = new Department(101, "KYC Tech");
		Department onboardingTeam = new Department(101, "Onboarding Tech");
		Department discoveryTeam = new Department(101, "Discovery Tech");

		departments.add(kycTeam);
		departments.add(onboardingTeam);
		departments.add(discoveryTeam);

		User aliceUser = new User(301, "Alice", kycTeam.getDeptId(), kycTeam.getDeptName());
		User amyUser = new User(302, "Amy", kycTeam.getDeptId(), kycTeam.getDeptName());
		User atkinsUser = new User(303, "Atkins", kycTeam.getDeptId(), kycTeam.getDeptName());

		User balakrishnanUser = new User(304, "Balakrishnan", onboardingTeam.getDeptId(), onboardingTeam.getDeptName());
		User billyUser = new User(305, "Billy", onboardingTeam.getDeptId(), onboardingTeam.getDeptName());
		User bhaskarUser = new User(306, "Bhaskar", onboardingTeam.getDeptId(), onboardingTeam.getDeptName());

		User charuUser = new User(307, "Charu", discoveryTeam.getDeptId(), discoveryTeam.getDeptName());
		User carlosUser = new User(308, "Carlos", discoveryTeam.getDeptId(), discoveryTeam.getDeptName());
		User camilaUser = new User(309, "Camila", discoveryTeam.getDeptId(), discoveryTeam.getDeptName());
		User chintuUser = new User(309, "Chintu", discoveryTeam.getDeptId(), discoveryTeam.getDeptName());

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

		Project aadhaarProj = new Project(501, "Aadhaar Integration Module");
		Project kycReadyProj = new Project(502, "KYC Readiness Tracker");
		Project sponsoredDiscoveryProj = new Project(503, "Sponsored Partners Discovery");

		projects.add(aadhaarProj);
		projects.add(kycReadyProj);
		projects.add(sponsoredDiscoveryProj);

		Task regWithAadharApi = new Task(701, "Register application at Aadhaar Developer Console",
				aadhaarProj.getProjId(), aadhaarProj.getProjName(), aliceUser.getUserId(), aliceUser.getUserName());
		Task whitelistAadharServerIps = new Task(702, "Whitelist Aadhaar Server IPs for web-hooks",
				aadhaarProj.getProjId(), aadhaarProj.getProjName(), amyUser.getUserId(), amyUser.getUserName());
		Task profileUsersPanNumber = new Task(703, "Profile User's PAN Number", kycReadyProj.getProjId(),
				kycReadyProj.getProjName(), balakrishnanUser.getUserId(), balakrishnanUser.getUserName());

		tasks.add(regWithAadharApi);
		tasks.add(whitelistAadharServerIps);
		tasks.add(profileUsersPanNumber);

	}

}
