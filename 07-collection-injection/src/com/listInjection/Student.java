package com.listInjection;

import java.util.List;

public class Student {

	private String studentName;

	private List<String> benefits; // -- common for all students

	private List<Course> courses; // -- many-to-many

	private List<String> qualifications; // -- specific for each student

	public void setStudentName(String studentName) {
		this.studentName = studentName;
	}

	public void setBenefits(List<String> benefits) {
		this.benefits = benefits;
	}

	public void setCourses(List<Course> courses) {
		this.courses = courses;
	}

	public void setQualifications(List<String> qualifications) {
		this.qualifications = qualifications;
	}

	public String getStudentName() {
		return studentName;
	}

	public List<String> getBenefits() {
		return benefits;
	}

	public List<Course> getCourses() {
		return courses;
	}

	public List<String> getQualifications() {
		return qualifications;
	}

	@Override
	public String toString() {
		return "Student [studentName=" + studentName + ", benefits=" + benefits + ", courses=" + courses
				+ ", qualifications=" + qualifications + "]";
	}

}
