package com.mainapp;

public class Project {

	// Static block is executed when the class is loaded into memory, before any
	// object of the class is created.
	static {
		System.out.println("[Project #static_block] Project class is loaded");
	}

	private String projectName;

	// Constructor is executed when an object of the class is created.
	public Project() {
		System.out.println("[Project #constructor] Project object (Spring bean) is created");
	}

	public String objectDisplay() {
		return "Project@" + Integer.toHexString(System.identityHashCode(this)) + "{projectName=\"" + projectName
				+ "\"}";
	}

	public void test() {
		System.out.println("[Project #test] Project bean " + objectDisplay() + " is working");
	}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	@Override
	public String toString() {
		return "Project [projectName=" + projectName + "]";
	}

}
