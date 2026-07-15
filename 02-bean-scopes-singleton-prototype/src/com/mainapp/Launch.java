package com.mainapp;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Launch {

	public static void main(String[] args) {

		System.out.println("===== Loading Spring IOC container from beans.xml =====\n");

		ApplicationContext context = new ClassPathXmlApplicationContext("beans.xml");

		System.out.println("\n===== Container loaded. All singleton-scoped beans are already created above. =====\n");

		// ---------------------------------------------------------
		// 1) Employee bean -> default scope = singleton
		// ---------------------------------------------------------
		System.out.println("----- Testing 'emp' bean (default scope = singleton) -----");
		Employee emp1 = context.getBean("emp", Employee.class);
		emp1.setEmployeeName("Alice");
		emp1.test();

		Employee emp2 = context.getBean("emp", Employee.class);
		emp2.test();

		System.out.println("emp1 = " + emp1);
		System.out.println("emp2 = " + emp2);
		System.out.println("emp1 == emp2 ? " + (emp1 == emp2) + "  --> Same object returned (singleton behavior)\n");

		// ---------------------------------------------------------
		// 2) Project bean -> scope = prototype
		// ---------------------------------------------------------
		System.out.println("----- Testing 'project' bean (scope = prototype) -----");
		Project project1 = context.getBean("project", Project.class);
		project1.setProjectName("Spring Migration");
		project1.test();

		Project project2 = context.getBean("project", Project.class);
		project2.test();

		System.out.println("project1 = " + project1);
		System.out.println("project2 = " + project2);
		System.out.println("project1 == project2 ? " + (project1 == project2)
				+ "  --> Different objects returned (prototype behavior)\n");

		// ---------------------------------------------------------
		// 3) Salary bean -> explicit scope = singleton
		// ---------------------------------------------------------
		System.out.println("----- Testing 'salary' bean (scope = singleton, explicitly set) -----");
		Salary salary1 = context.getBean("salary", Salary.class);
		salary1.setBasicPay(50000.0);
		salary1.test();

		Salary salary2 = context.getBean("salary", Salary.class);
		salary2.test();

		System.out.println("salary1 = " + salary1);
		System.out.println("salary2 = " + salary2);
		System.out.println(
				"salary1 == salary2 ? " + (salary1 == salary2) + "  --> Same object returned (singleton behavior)\n");

		// ---------------------------------------------------------
		// Close the context (releases singleton bean resources)
		// ---------------------------------------------------------
		((ClassPathXmlApplicationContext) context).close();
	}

}
