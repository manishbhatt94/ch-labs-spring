package com.stringCleanDemo;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.stringCleanDemo.bean.DatabaseConfig;

public class MainApp {

	public static void main(String[] args) {

		System.out.println("--- Starting Spring Application Context ---\n");

		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(
				"com/stringCleanDemo/applicationContext.xml");

		System.out.println("\n--- Context Fully Initialized ---");

		DatabaseConfig config = context.getBean("myDatabaseConfig", DatabaseConfig.class);
		System.out.println("\nMain App Verified URL: '" + config.getConnectionUrl() + "'");

		context.close();

	}

}
