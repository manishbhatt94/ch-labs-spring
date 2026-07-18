package com.mainapp.withSpringCallbackInterfaces;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Launch {

	public static void main(String[] args) {

		ApplicationContext applicationContext = new ClassPathXmlApplicationContext(
				"com/mainapp/withSpringCallbackInterfaces/beans.xml");

		EmployeeReader reader = applicationContext.getBean("employeeReader", EmployeeReader.class);

		reader.readRecords();

		((ClassPathXmlApplicationContext) applicationContext).close();

	}

}
