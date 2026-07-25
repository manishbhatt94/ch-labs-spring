package com.setInjection;

import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.TreeSet;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class MainApp {

	public static void main(String[] args) {

		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("com/setInjection/beans.xml");

		System.out.println("######### Set Injection (XML) Demo ################\n\n");

		@SuppressWarnings("unchecked")
		HashSet<String> skillTags = context.getBean("uniqueSkillTags", HashSet.class);
		System.out.println("skillTags.getClass() => " + skillTags.getClass());
		System.out.println("skillTags: " + skillTags + "   (iteration order not guaranteed)");

		System.out.println();

		@SuppressWarnings("unchecked")
		LinkedHashSet<String> examCities = context.getBean("preferredExamCities", LinkedHashSet.class);
		System.out.println("examCities.getClass() => " + examCities.getClass());
		System.out.println("examCities: " + examCities + "   (duplicate 'Bengaluru' dropped, insertion order kept)");

		System.out.println();

		@SuppressWarnings("unchecked")
		TreeSet<String> courseCodes = context.getBean("sortedCourseCodes", TreeSet.class);
		System.out.println("courseCodes.getClass() => " + courseCodes.getClass());
		System.out.println("courseCodes: " + courseCodes + "   (duplicate 'CS105' dropped, always sorted)");

		System.out.println();

		@SuppressWarnings("unchecked")
		Set<String> commonHobbies = context.getBean("commonHobbies", Set.class);
		System.out.println("commonHobbies.getClass() => " + commonHobbies.getClass());
		System.out.println("commonHobbies: " + commonHobbies);

		System.out.println();

		Student s1 = context.getBean("student-1", Student.class);
		Student s2 = context.getBean("student-2", Student.class);

		System.out.println("Student #1:\n" + s1 + "\n");
		System.out.println("Student #2:\n" + s2 + "\n");

		System.out.println("s1.getEnrolledCourses().getClass() => " + s1.getEnrolledCourses().getClass()
				+ "   (plain <set/> nested in a <property> -> defaults to LinkedHashSet)");
		System.out.println("s1.getEnrolledCourses().size() => " + s1.getEnrolledCourses().size()
				+ "   (5 <ref> entries declared in XML, only 3 unique courses survive because of"
				+ " Course#equals()/#hashCode(); without that override it would be 4)");

		System.out.println("s1.getCertifications().getClass() => " + s1.getCertifications().getClass());

		System.out.println("\n→ s1.getHobbies() == s2.getHobbies() ==> " + (s1.getHobbies() == s2.getHobbies())
				+ "   (both reference the same singleton 'commonHobbies' bean)");
		System.out.println("\n→ s1.getEnrolledCourses() == s2.getEnrolledCourses() ==> "
				+ (s1.getEnrolledCourses() == s2.getEnrolledCourses())
				+ "   (each student got its own inline <set>, even though some Course beans are shared)");

		context.close();

	}

}
