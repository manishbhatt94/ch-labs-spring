package com.mapInjection;

import java.util.Objects;

public class Course {

	private String courseName;

	private int courseCredits;

	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}

	public void setCourseCredits(int courseCredits) {
		this.courseCredits = courseCredits;
	}

	/*
	 * IMPORTANT for Set semantics: A java.util.Set relies on equals()/hashCode() to
	 * decide whether two elements are "the same" and hence should not both be kept.
	 *
	 * Without this override, Course would fall back on Object's identity-based
	 * equals(). Two Course beans with identical courseName/courseCredits, but
	 * declared as two separate <bean> entries in XML (see courseDatabase and
	 * courseDatabaseDuplicate in beans.xml), would then be treated as DIFFERENT
	 * elements -- BOTH would end up in a Set, even though they logically represent
	 * the same course.
	 *
	 * With this override, such value-equal-but-reference-distinct beans get
	 * correctly de-duplicated when added to a Set (see student-1's enrolledCourses
	 * in beans.xml, and the size check in MainApp).
	 */
	@Override
	public int hashCode() {
		return Objects.hash(Integer.valueOf(courseCredits), courseName);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		Course other = (Course) obj;
		return courseCredits == other.courseCredits && Objects.equals(courseName, other.courseName);
	}

	@Override
	public String toString() {
		return "Course [courseName=" + courseName + ", courseCredits=" + courseCredits + "]";
	}

}
