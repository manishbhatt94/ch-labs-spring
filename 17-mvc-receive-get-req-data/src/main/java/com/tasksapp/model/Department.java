package com.tasksapp.model;

public class Department {

	private int deptId;

	private String deptName;

	private static int nextId = 101;

	public Department() {
		super();
	}

	public Department(String deptName) {
		this(nextId++, deptName);
	}

	private Department(int deptId, String deptName) {
		super();
		this.deptId = deptId;
		this.deptName = deptName;
	}

	public int getDeptId() {
		return deptId;
	}

	public void setDeptId(int deptId) {
		this.deptId = deptId;
	}

	public String getDeptName() {
		return deptName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

	@Override
	public String toString() {
		return "Department [deptId=" + deptId + ", deptName=" + deptName + "]";
	}

}
