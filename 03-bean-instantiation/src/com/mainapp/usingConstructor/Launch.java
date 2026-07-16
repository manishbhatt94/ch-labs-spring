package com.mainapp.usingConstructor;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Launch {

	public static void main(String[] args) {

		ApplicationContext applicationContext = new ClassPathXmlApplicationContext(
				"com/mainapp/usingConstructor/beans.xml");

		Calculator calc = applicationContext.getBean("calc", Calculator.class);
		System.out.println("calc.add(5, 6) = " + calc.add(5, 6));
		System.out.println("calc.add(9, 21) = " + calc.add(9, 21));
		System.out.println("calc.add(3, 13) = " + calc.add(3, 13));

		Engine economyHatchback = applicationContext.getBean("economyHatchback", Engine.class);
		System.out.println("Economy Hatchback = " + economyHatchback);

		Engine standardSedan = applicationContext.getBean("standardSedan", Engine.class);
		System.out.println("Standard Sedan = " + standardSedan);

		Engine performanceHotHatch = applicationContext.getBean("performanceHotHatch", Engine.class);
		System.out.println("Performance Hot Hatch = " + performanceHotHatch);

		Engine sportsSedan = applicationContext.getBean("sportsSedan", Engine.class);
		System.out.println("Sports Sedan = " + sportsSedan);

		Engine muscleCar = applicationContext.getBean("muscleCar", Engine.class);
		System.out.println("Muscle Car = " + muscleCar);

		((ClassPathXmlApplicationContext) applicationContext).close();

	}

}
