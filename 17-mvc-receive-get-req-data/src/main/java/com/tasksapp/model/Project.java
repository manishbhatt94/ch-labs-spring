package com.tasksapp.model;

public class Project {

	private int projId;

	private String projName;

	private static int nextId = 501;

	public Project() {
		super();
		this.projId = nextId++;
	}

	public Project(String projName) {
		this(nextId++, projName);
	}

	private Project(int projId, String projName) {
		super();
		this.projId = projId;
		this.projName = projName;
	}

	public int getProjId() {
		return projId;
	}

	public void setProjId(int projId) {
		this.projId = projId;
	}

	public String getProjName() {
		return projName;
	}

	public void setProjName(String projName) {
		this.projName = projName;
	}

	@Override
	public String toString() {
		return "Project [projId=" + projId + ", projName=" + projName + "]";
	}

}
