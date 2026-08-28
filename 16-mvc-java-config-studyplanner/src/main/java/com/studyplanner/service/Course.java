package com.studyplanner.service;

public class Course {

	private int id;
	private String name;
	private String description;

	// Used by Spring when binding form fields via @ModelAttribute —
	// Spring needs a no-arg constructor to instantiate the object first,
	// then calls setters for each form field.
	public Course() {
	}

	public Course(int id, String name, String description) {
		this.id = id;
		this.name = name;
		this.description = description;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}
