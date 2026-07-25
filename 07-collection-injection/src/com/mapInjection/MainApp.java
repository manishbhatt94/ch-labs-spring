package com.mapInjection;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class MainApp {

	public static void main(String[] args) {

		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("com/mapInjection/beans.xml");

		System.out.println("######### Map Injection (XML) Demo ################\n\n");

		context.close();

	}

}
