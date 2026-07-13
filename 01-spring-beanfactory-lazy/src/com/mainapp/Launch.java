package com.mainapp;

import org.springframework.beans.factory.xml.XmlBeanFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

public class Launch {

	public static void main(String[] args) {

		System.out.println("--- STEP 1: Creating Resource Object ---");
		// Instructs Spring to look inside the Classpath root for beans.xml
		Resource xmlResource = new ClassPathResource("beans.xml");

		System.out.println("\n--- STEP 2: Initializing XmlBeanFactory ---");
		// The container is instantiated here, but NO beans are constructed yet!
		XmlBeanFactory factory = new XmlBeanFactory(xmlResource);
		System.out.println("[Container Status] XmlBeanFactory is fully loaded and ready.");

		System.out.println("\n--- STEP 3: Requesting Employee Bean for the FIRST time ---");
		// This line forces the lazy-loading BeanFactory to instantiate the object
		Employee employeeInstance = factory.getBean("emp", Employee.class);

		// Or, using casting instead of specifying the class type:
		// with this overload of getBean():
		// public Object getBean(String name)
//		Employee employeeInstance = (Employee) factory.getBean("emp");

		System.out.println("\n--- STEP 4: Executing Business Method ---");
		employeeInstance.test();

	}

}
