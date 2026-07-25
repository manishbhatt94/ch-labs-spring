package com.propsInjection;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class MainApp {

	public static void main(String[] args) {

		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("com/propsInjection/beans.xml");

		System.out.println("######### Properties Injection (XML) Demo ################\n\n");

		context.close();

	}

}
