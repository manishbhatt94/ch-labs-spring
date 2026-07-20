package com.alertsDemo;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class MainApp {

	public static void main(String[] args) {

		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("com/alertsDemo/beans.xml");

		AlertService debug = context.getBean("debugAlertService", AlertService.class);
		debug.sendAlert("Database connection pool ready in 240 milliseconds");
		System.out.println();

		AlertService info = context.getBean("infoAlertService", AlertService.class);
		info.sendAlert("Nightly backup completed");
		System.out.println();

		AlertService critical = context.getBean("criticalAlertService", AlertService.class);
		critical.sendAlert("Server disk usage above 95%");

		context.close();

	}

}
