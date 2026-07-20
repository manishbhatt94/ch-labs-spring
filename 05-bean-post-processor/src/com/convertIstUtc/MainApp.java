package com.convertIstUtc;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class MainApp {

	public static void main(String[] args) {

		System.out.println("====== Bootstrap Spring Engine ======\n");

		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("com/convertIstUtc/beans.xml");

		System.out.println("\n====== Engine Bootstrapped Successfully ======\n");

		context.close();

	}

}
