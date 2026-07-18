package com.mainapp.withAnnotationsJSR250;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Launch {

	public static void main(String[] args) {

		ApplicationContext applicationContext = new ClassPathXmlApplicationContext(
				"com/mainapp/withAnnotationsJSR250/beans.xml");

		StudentReader reader = applicationContext.getBean("studentReader", StudentReader.class);

		reader.readRecords();

		((ClassPathXmlApplicationContext) applicationContext).close();

	}

}
