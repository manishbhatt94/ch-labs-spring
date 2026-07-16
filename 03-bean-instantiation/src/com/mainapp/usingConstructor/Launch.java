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

		System.out.println();

		Engine economyHatchbackEngine = applicationContext.getBean("economyHatchback", Engine.class);
		System.out.println("Economy Hatchback = " + economyHatchbackEngine);

		Engine standardSedanEngine = applicationContext.getBean("standardSedan", Engine.class);
		System.out.println("Standard Sedan = " + standardSedanEngine);

		Engine performanceHotHatchEngine = applicationContext.getBean("performanceHotHatch", Engine.class);
		System.out.println("Performance Hot Hatch = " + performanceHotHatchEngine);

		Engine sportsSedanEngine = applicationContext.getBean("sportsSedan", Engine.class);
		System.out.println("Sports Sedan = " + sportsSedanEngine);

		Engine muscleCarEngine = applicationContext.getBean("muscleCar", Engine.class);
		System.out.println("Muscle Car = " + muscleCarEngine);

		System.out.println();

		Car altoCar = applicationContext.getBean("alto", Car.class);
		System.out.println("Car: Alto K10 = " + altoCar);

		Car volkswagenVirtusCar = applicationContext.getBean("volkswagenVirtus", Car.class);
		System.out.println("Car: Volkswagen Virtus = " + volkswagenVirtusCar);

		Car porschePanameraCar = applicationContext.getBean("porschePanamera", Car.class);
		System.out.println("Car: Porsche Panamera = " + porschePanameraCar);

		Car lamborghiniHuracanCar = applicationContext.getBean("lamborghiniHuracan", Car.class);
		System.out.println("Car: Lamborghini Huracan = " + lamborghiniHuracanCar);

		((ClassPathXmlApplicationContext) applicationContext).close();

	}

}
