package com.mapInjection;

import java.util.Map;

public class Student {

	private String studentName;

	private Map<String, Integer> subjectMarks; // -- subject name -> marks scored, order of study matters

	private Map<String, Course> weeklyTimetable; // -- day of week -> scheduled Course (may be null on a holiday)

	public void setStudentName(String studentName) {
		this.studentName = studentName;
	}

	public void setSubjectMarks(Map<String, Integer> subjectMarks) {
		this.subjectMarks = subjectMarks;
	}

	public void setWeeklyTimetable(Map<String, Course> weeklyTimetable) {
		this.weeklyTimetable = weeklyTimetable;
	}

	public String getStudentName() {
		return studentName;
	}

	public Map<String, Integer> getSubjectMarks() {
		return subjectMarks;
	}

	public Map<String, Course> getWeeklyTimetable() {
		return weeklyTimetable;
	}

	@Override
	public String toString() {
		return "Student [studentName=" + studentName + ", subjectMarks=" + subjectMarks + ", weeklyTimetable="
				+ weeklyTimetable + "]";
	}

}
