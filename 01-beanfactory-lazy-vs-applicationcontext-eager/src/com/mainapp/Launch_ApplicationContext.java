package com.mainapp;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Launch_ApplicationContext {

	public static void main(String[] args) {

		System.out.println("===== Eager Bean Initialization with ApplicationContext "
				+ "(ClassPathXmlApplicationContext impl) =====\n");

		System.out.println("\n--- STEP 1: Initializing ClassPathXmlApplicationContext ---");

		// The below line will eagerly load all beans defined in the XML configuration
		// file:
		ApplicationContext applicationContext = new ClassPathXmlApplicationContext("beans.xml");
		// --------------- ^^^^^^^^^^^^^^^^^^
		// Eclipse IDE Warning - Resource leak: 'applicationContext' is never closed.

		System.out.println("\n[Container Status] ClassPathXmlApplicationContext is fully loaded and ready.");

		System.out.println("\n--- STEP 2: Requesting Employee Bean for the FIRST time ---");
		Employee employeeInstance = applicationContext.getBean("emp", Employee.class);

		// Or, using casting instead of specifying the class type:
		// with this overload of getBean():
		// public Object getBean(String name)
//		Employee employeeInstance = (Employee) factory.getBean("emp");

		System.out.println("\n--- STEP 4: Executing Business Method ---");
		employeeInstance.test();

		// ---------------------------------------------------------
		// Close the context (releases singleton bean resources)
		// ---------------------------------------------------------
		((ClassPathXmlApplicationContext) applicationContext).close();

	}

}
