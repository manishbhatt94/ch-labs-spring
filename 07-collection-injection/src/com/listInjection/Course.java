package com.listInjection;

public class Course {

	private String courseName;

	private int courseCredits;

	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}

	public void setCourseCredits(int courseCredits) {
		this.courseCredits = courseCredits;
	}

	@Override
	public String toString() {
		return "Course [courseName=" + courseName + ", courseCredits=" + courseCredits + "]";
	}

}
