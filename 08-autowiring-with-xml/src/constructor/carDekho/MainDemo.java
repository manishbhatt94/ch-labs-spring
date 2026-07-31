package constructor.carDekho;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import constructor.carDekho.car.Car;
import constructor.carDekho.car.EntryLevelCar;

public class MainDemo {

	public static void main(String[] args) {

		System.out.println("####### Auto-wiring mode \"constructor\" Demo (XML config) ###########\n");

		System.out.println("\n=== 1) beans-constructor-basics.xml ===");
		System.out.println("=======================================\n\n");
		try (ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext(
				"constructor/carDekho/resources/beans-constructor-basics.xml")) {
			ctx.getBean("myCar", EntryLevelCar.class).describe("Basic Constructor Autowiring Demo");
		}
		System.out.println();

		System.out.println("\n=== 2) beans-constructor-greedy-selection.xml ===");
		System.out.println("=================================================\n\n");
		try (ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext(
				"constructor/carDekho/resources/beans-constructor-greedy-selection.xml")) {
			ctx.getBean("myCar", Car.class).describe("Greedy Constructor Selection Demo");
		}
		System.out.println();

	}

}
