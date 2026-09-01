package com.tasksapp.dto;

public class TaskFilter {

	private String taskName;

	// Using wrapper type 'Integer', instead of primitive type 'int' to allow null
	// values for optional filtering
	private Integer linkedProjId;

	// Using wrapper type 'Integer', instead of primitive type 'int' to allow null
	// values for optional filtering
	private Integer assigneeId;

	public TaskFilter() {
		super();
	}

	public String getTaskName() {
		return taskName;
	}

	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}

	public Integer getLinkedProjId() {
		return linkedProjId;
	}

	public void setLinkedProjId(Integer linkedProjId) {
		this.linkedProjId = linkedProjId;
	}

	public Integer getAssigneeId() {
		return assigneeId;
	}

	public void setAssigneeId(Integer assigneeId) {
		this.assigneeId = assigneeId;
	}

	@Override
	public String toString() {
		return "TaskFilter [taskName=" + taskName + ", linkedProjId=" + linkedProjId + ", assigneeId=" + assigneeId
				+ "]";
	}

}
