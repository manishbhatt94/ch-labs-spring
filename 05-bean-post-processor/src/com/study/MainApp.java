package com.study;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.study.bean.DatabaseConfig;

public class MainApp {

	public static void main(String[] args) {

		System.out.println("--- Starting Spring Application Context ---");

		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("com/study/applicationContext.xml");

		System.out.println("--- Context Fully Initialized ---");

		DatabaseConfig config = context.getBean("myDatabaseConfig", DatabaseConfig.class);
		System.out.println("Main App Verified URL: '" + config.getConnectionUrl() + "'");

		context.close();

	}

}
