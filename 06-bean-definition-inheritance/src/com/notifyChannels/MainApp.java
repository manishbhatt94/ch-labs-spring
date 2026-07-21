package com.notifyChannels;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class MainApp {

	public static void main(String[] args) {

		ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("com/notifyChannels/beans.xml");

		System.out.println("\n===== Demo of EmailNotifier type bean: =======");
		ctx.getBean("emailNotifier", EmailNotifier.class).notify("Password reset requested");

		System.out.println("\n===== Demo of SmsNotifier type bean: =======");
		ctx.getBean("smsNotifier", SmsNotifier.class).notify("Your OTP is 4821");

		System.out.println("\n===== Demo of PushNotifier type bean: =======");
		ctx.getBean("pushNotifier", PushNotifier.class).notify("New message received");

		ctx.close();

	}

}
