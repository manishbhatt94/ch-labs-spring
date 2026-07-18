package com.mainapp.withXmlAttributes;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Launch {

	public static void main(String[] args) {

		ApplicationContext applicationContext = new ClassPathXmlApplicationContext(
				"com/mainapp/withXmlAttributes/beans.xml");

		CourseReader reader = applicationContext.getBean("courseReader", CourseReader.class);

		reader.readRecords();

		((ClassPathXmlApplicationContext) applicationContext).close();

	}

}
