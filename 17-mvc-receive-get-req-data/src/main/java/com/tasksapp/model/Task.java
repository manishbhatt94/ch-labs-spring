package com.tasksapp.model;

public class Task {

	private int taskId;

	private String taskName;

	private int linkedProjId;

	private String linkedProjName;

	private int assigneeId;

	private String assigneeName;

	public Task() {
		super();
	}

	public Task(int taskId, String taskName, int linkedProjId, String linkedProjName, int assigneeId,
			String assigneeName) {
		super();
		this.taskId = taskId;
		this.taskName = taskName;
		this.linkedProjId = linkedProjId;
		this.linkedProjName = linkedProjName;
		this.assigneeId = assigneeId;
		this.assigneeName = assigneeName;
	}

	public int getTaskId() {
		return taskId;
	}

	public void setTaskId(int taskId) {
		this.taskId = taskId;
	}

	public String getTaskName() {
		return taskName;
	}

	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}

	public int getLinkedProjId() {
		return linkedProjId;
	}

	public void setLinkedProjId(int linkedProjId) {
		this.linkedProjId = linkedProjId;
	}

	public String getLinkedProjName() {
		return linkedProjName;
	}

	public void setLinkedProjName(String linkedProjName) {
		this.linkedProjName = linkedProjName;
	}

	public int getAssigneeId() {
		return assigneeId;
	}

	public void setAssigneeId(int assigneeId) {
		this.assigneeId = assigneeId;
	}

	public String getAssigneeName() {
		return assigneeName;
	}

	public void setAssigneeName(String assigneeName) {
		this.assigneeName = assigneeName;
	}

	@Override
	public String toString() {
		return "Task [taskId=" + taskId + ", taskName=" + taskName + ", linkedProjId=" + linkedProjId
				+ ", linkedProjName=" + linkedProjName + ", assigneeId=" + assigneeId + ", assigneeName=" + assigneeName
				+ "]";
	}

}
