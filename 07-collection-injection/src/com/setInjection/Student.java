package com.setInjection;

import java.util.Set;

public class Student {

	private String studentName;

	private Set<String> hobbies; // -- common for all students, insertion order matters -> LinkedHashSet

	private Set<Course> enrolledCourses; // -- many-to-many, must never contain the same course twice

	private Set<String> certifications; // -- specific to each student, always shown sorted -> TreeSet

	public void setStudentName(String studentName) {
		this.studentName = studentName;
	}

	public void setHobbies(Set<String> hobbies) {
		this.hobbies = hobbies;
	}

	public void setEnrolledCourses(Set<Course> enrolledCourses) {
		this.enrolledCourses = enrolledCourses;
	}

	public void setCertifications(Set<String> certifications) {
		this.certifications = certifications;
	}

	public String getStudentName() {
		return studentName;
	}

	public Set<String> getHobbies() {
		return hobbies;
	}

	public Set<Course> getEnrolledCourses() {
		return enrolledCourses;
	}

	public Set<String> getCertifications() {
		return certifications;
	}

	@Override
	public String toString() {
		return "Student [studentName=" + studentName + ", hobbies=" + hobbies + ", enrolledCourses=" + enrolledCourses
				+ ", certifications=" + certifications + "]";
	}

}
