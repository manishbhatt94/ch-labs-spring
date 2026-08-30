package com.tasksapp.model;

public class User {

	private int userId;

	private String userName;

	private int workDeptId;

	private String workDeptName;

	private static int nextId = 301;

	public User() {
		super();
	}

	public User(String userName, int workDeptId, String workDeptName) {
		this(nextId++, userName, workDeptId, workDeptName);
	}

	private User(int userId, String userName, int workDeptId, String workDeptName) {
		super();
		this.userId = userId;
		this.userName = userName;
		this.workDeptId = workDeptId;
		this.workDeptName = workDeptName;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public int getWorkDeptId() {
		return workDeptId;
	}

	public void setWorkDeptId(int workDeptId) {
		this.workDeptId = workDeptId;
	}

	public String getWorkDeptName() {
		return workDeptName;
	}

	public void setWorkDeptName(String workDeptName) {
		this.workDeptName = workDeptName;
	}

	@Override
	public String toString() {
		return "User [userId=" + userId + ", userName=" + userName + ", workDeptId=" + workDeptId + ", workDeptName="
				+ workDeptName + "]";
	}

}
