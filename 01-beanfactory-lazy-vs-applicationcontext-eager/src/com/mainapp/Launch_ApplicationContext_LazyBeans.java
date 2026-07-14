package com.mainapp;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Launch_ApplicationContext_LazyBeans {

	public static void main(String[] args) {

		System.out.println("===== Eager Bean Initialization with ApplicationContext "
				+ "(With some beans configured as LAZY) =====\n");

		System.out.println("\n--- STEP 1: Initializing ClassPathXmlApplicationContext ---");

		// The below line will eagerly load all beans defined in the XML configuration
		// file:
		@SuppressWarnings("resource")
		ApplicationContext applicationContext = new ClassPathXmlApplicationContext("beans.xml");
		// --------------- ^^^^^^^^^^^^^^^^^^
		// Eclipse IDE Warning - Resource leak: 'applicationContext' is never closed.

		// Note: The above line will eagerly load all beans defined in the XML
		// configuration file, but the Project & Salary beans are configured with
		// lazy-init="true", so they will not be instantiated until requested using the
		// getBean() method.

		System.out.println("\n[Container Status] ClassPathXmlApplicationContext is fully loaded and ready.");

		System.out.println("\n--- STEP 2: Requesting Employee Bean for the FIRST time ---");
		Employee employeeInstance = applicationContext.getBean("emp", Employee.class);

		System.out.println("\n--- STEP 3: Executing Employee#test() Method ---");
		employeeInstance.test();

		// Now, calling employeeInstance.getPermission().
		// If return value is "access_granted", then we will request the Project and
		// Salary beans, and they will be instantiated at that time (lazy loading).
		// If return value is "access_denied", then we will NOT request the Project and
		// Salary beans, and they will NOT be instantiated at all.

		System.out.println("\n[Fetching Permission] Calling Employee#getPermission()...");
		String permission = employeeInstance.getPermission();
		System.out.println("\n[Permission Status] Permission returned by Employee#getPermission(): " + permission);

		if (permission.equals("access_granted")) {
			System.out.println("\n--- STEP 4: Requesting Project and Salary Beans ---");
			Project projectInstance = applicationContext.getBean("project", Project.class);
			Salary salaryInstance = applicationContext.getBean("salary", Salary.class);
			System.out.println("\n--- STEP 5: Executing Project#test() and Salary#test() Methods ---");
			projectInstance.test();
			salaryInstance.test();
		} else {
			System.out.println("\n[Access Denied] You do not have permission to access Project and Salary beans.");
		}

	}

}
