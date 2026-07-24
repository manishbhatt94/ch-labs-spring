package com.listInjection;

import java.util.LinkedList;
import java.util.List;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class MainApp {

	public static void main(String[] args) {

		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("com/listInjection/beans.xml");

		@SuppressWarnings("unchecked")
		LinkedList<String> planets = context.getBean("planets", LinkedList.class);

		System.out.println("planets.getClass() => " + planets.getClass());
		System.out.println("planets: " + planets);

		@SuppressWarnings("unchecked")
		LinkedList<String> planets2 = context.getBean("planets", LinkedList.class);

		System.out.println("planets == planets2 ---> " + (planets == planets2) + ". (i.e. singleton)");

		System.out.println();

		@SuppressWarnings("unchecked")
		List<String> benefits = context.getBean("studBenefits", List.class);
		// -------------------- ^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^
		// "Eclipse Warning" Type safety: The expression of type List needs unchecked
		// conversion to conform to List<String>
		System.out.println("benefits.getClass() => " + benefits.getClass());
		System.out.println("benefits: " + benefits);

		System.out.println();

		Student s1 = context.getBean("student-1", Student.class);
		Student s2 = context.getBean("student-2", Student.class);

		System.out.println("Student #1:\n" + s1 + "\n");
		System.out.println("Student #2:\n" + s2 + "\n");

		System.out.println("\n→ s1.getBenefits() == s2.getBenefits() ==> " + (s1.getBenefits() == s2.getBenefits()));
		System.out.println("\n→ s1.getCourses() == s2.getCourses() ==> " + (s1.getCourses() == s2.getCourses()));
		System.out.println("\n→ s1.getQualifications() == s2.getQualifications() ==> "
				+ (s1.getQualifications() == s2.getQualifications()));

		context.close();

	}

}
