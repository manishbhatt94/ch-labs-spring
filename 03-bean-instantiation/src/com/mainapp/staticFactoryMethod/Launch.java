package com.mainapp.staticFactoryMethod;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Launch {

	public static void main(String[] args) {

		ApplicationContext applicationContext = new ClassPathXmlApplicationContext(
				"com/mainapp/staticFactoryMethod/beans.xml");

		BusTicket onwardTicket = applicationContext.getBean("onwardTicket", BusTicket.class);
		System.out.println("Onward Ticket = " + onwardTicket);

		BusTicket returnTicket = applicationContext.getBean("returnTicket", BusTicket.class);
		System.out.println("Return Ticket = " + returnTicket);

		System.out.println();

		UserProfile nullUser1 = applicationContext.getBean("nullUser1", UserProfile.class);
		System.out.println("User: nullUser1 = " + nullUser1);

		UserProfile noob1947User = applicationContext.getBean("noob1947", UserProfile.class);
		System.out.println("User: noob1947 = " + noob1947User);

		System.out.println();

		UserProfile nullUser2 = applicationContext.getBean("nullUser2", UserProfile.class);
		System.out.println("User: nullUser2 = " + nullUser2);

		UserProfile ambedkar1950zyxUser = applicationContext.getBean("ambedkar1950zyx", UserProfile.class);
		System.out.println("User: ambedkar1950zyx = " + ambedkar1950zyxUser);

		((ClassPathXmlApplicationContext) applicationContext).close();

	}

}
