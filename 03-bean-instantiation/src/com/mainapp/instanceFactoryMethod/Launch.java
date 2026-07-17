package com.mainapp.instanceFactoryMethod;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Launch {

	public static void main(String[] args) {

		ApplicationContext applicationContext = new ClassPathXmlApplicationContext(
				"com/mainapp/instanceFactoryMethod/beans.xml");

		Organization spacexOrg = applicationContext.getBean("spacexOrg", Organization.class);
		System.out.println("Organization: SpaceX = " + spacexOrg);

		System.out.println("\n\nEmployees of SpaceX Organization:\n");

		Employee employeeGwynne = applicationContext.getBean("employeeGwynne", Employee.class);
		System.out.println("Employee: Gwynne Shotwell = " + employeeGwynne);

		System.out.println();

		Employee employeeElon = applicationContext.getBean("employeeElon", Employee.class);
		System.out.println("Employee: Elon Musk = " + employeeElon);

		((ClassPathXmlApplicationContext) applicationContext).close();

	}

}
