package com.mapInjection;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class MainApp {

	public static void main(String[] args) {

		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("com/mapInjection/beans.xml");

		System.out.println("######### Map Injection (XML) Demo ################\n\n");

		@SuppressWarnings("unchecked")
		HashMap<String, String> pincodeToCity = context.getBean("pincodeToCity", HashMap.class);
		System.out.println("pincodeToCity.getClass() => " + pincodeToCity.getClass());
		System.out.println("pincodeToCity: " + pincodeToCity + "   (iteration order not guaranteed)");

		System.out.println();

		@SuppressWarnings("unchecked")
		TreeMap<String, Double> subjectAverages = context.getBean("sortedSubjectAverages", TreeMap.class);
		System.out.println("subjectAverages.getClass() => " + subjectAverages.getClass());
		System.out
				.println("subjectAverages: " + subjectAverages + "   (always sorted by key, regardless of XML order)");

		System.out.println();

		@SuppressWarnings("unchecked")
		Map<Course, String> courseCoordinators = context.getBean("courseCoordinators", Map.class);
		System.out.println("courseCoordinators.getClass() => " + courseCoordinators.getClass());
		System.out.println("courseCoordinators.size() => " + courseCoordinators.size()
				+ "   (4 <entry> lines in XML, but only 3 survive -- see comment in beans.xml)");
		System.out.println("courseCoordinators: " + courseCoordinators);
		Course dbCourseCopy = new Course();
		dbCourseCopy.setCourseName("Database Management Systems");
		dbCourseCopy.setCourseCredits(7);
		System.out.println("Coordinator for 'Database Management Systems' course "
				+ "(created freshly outside IOC Container) =>\n     " + courseCoordinators.get(dbCourseCopy));

		System.out.println();

		Student s1 = context.getBean("student-1", Student.class);
		Student s2 = context.getBean("student-2", Student.class);

		System.out.println("Student #1:\n" + s1 + "\n");
		System.out.println("Student #2:\n" + s2 + "\n");

		System.out.println("s1.getSubjectMarks().getClass() => " + s1.getSubjectMarks().getClass()
				+ "   (plain <map/> nested in <property> -> defaults to LinkedHashMap)");
		System.out.println("s1.getSubjectMarks().size() => " + s1.getSubjectMarks().size()
				+ "   (4 <entry> lines for 'English' x2 declared, only 3 unique keys survive)");
		System.out.println("s1.getSubjectMarks().get(\"English\") => " + s1.getSubjectMarks().get("English")
				+ "   (last value for the repeated key wins, not the first)");

		System.out.println();

		System.out.println("s1.getWeeklyTimetable().get(\"Sunday\") => " + s1.getWeeklyTimetable().get("Sunday")
				+ "   (Map values, unlike Set elements, are allowed to be null)");

		context.close();

		System.out.println();

	}

}
