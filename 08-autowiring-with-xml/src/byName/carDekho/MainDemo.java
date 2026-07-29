package byName.carDekho;

import java.math.BigDecimal;
import java.util.Arrays;

import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import byName.carDekho.car.Car;
import byName.carDekho.parts.Engine;

public class MainDemo {

	public static void main(String[] args) {

		System.out.println("\n=== 1) beans-byname-main.xml ===");
		System.out.println("================================\n\n");
		try (ConfigurableApplicationContext ctx = new ClassPathXmlApplicationContext(
				"byName/carDekho/resources/beans-byname-main.xml")) {
			ctx.getBean("car1", Car.class).describe("car1 - plain byName");
			ctx.getBean("car2", Car.class).describe("car2 - explicit <property> overrides autowiring");
			ctx.getBean("car3", Car.class).describe("car3 - autowire-candidate=false excludes gps");
			ctx.getBean("car4", Car.class).describe("car4 - explicit ref bypasses autowire-candidate=false");
		}
		System.out.println();

		System.out.println("\n=== 2) beans-byname-default-candidates.xml ===");
		System.out.println("==============================================\n\n");
		try (ConfigurableApplicationContext ctx = new ClassPathXmlApplicationContext(
				"byName/carDekho/resources/beans-byname-default-candidates.xml")) {
			ctx.getBean("car5", Car.class).describe("car5 - gps excluded via default-autowire-candidates");
			ctx.getBean("car6", Car.class).describe("car6 - explicit ref still bypasses the pattern exclusion");
		}
		System.out.println();

		System.out.println("\n=== 3) beans-byname-simple-and-collections.xml ===");
		System.out.println("==================================================\n\n");
		try (ConfigurableApplicationContext ctx = new ClassPathXmlApplicationContext(
				"byName/carDekho/resources/beans-byname-simple-and-collections.xml")) {
			String color = ctx.getBean("color", String.class);
			System.out.println("color: " + color);
			BigDecimal dealershipPrice = ctx.getBean("dealershipPrice", BigDecimal.class);
			System.out.println("dealershipPrice: " + dealershipPrice);
			String[] serviceHistory = ctx.getBean("serviceHistory", String[].class);
			System.out.println("serviceHistory: " + Arrays.toString(serviceHistory));
			Engine[] spareEngines = ctx.getBean("spareEngines", Engine[].class);
			System.out.println("spareEngines: " + Arrays.toString(spareEngines));
			System.out.println();
			ctx.getBean("car_simple1", Car.class)
					.describe("car_simple1 - color/dealershipPrice/serviceHistory left null "
							+ "(simple types and arrays-of-simple-types are never autowire candidates); "
							+ "engine/transmission/spareEngines/accessories/vehicleId still wired by name");
			ctx.getBean("car_simple2", Car.class)
					.describe("car_simple2 - color/dealershipPrice/serviceHistory set explicitly, "
							+ "including a literal nested <array> for the String[] property");
			ctx.getBean("car_simple3", Car.class)
					.describe("car_simple3 - spareEngines/accessories/vehicleId auto-wired fields "
							+ "overridden using <property>, and field 'gps' also manually wired");
			ctx.getBean("car_arrays1", Car.class)
					.describe("car_arrays1 - declared before the 'spareEngines' bean definition; "
							+ "still gets it wired (declaration order doesn't matter for byName)");
			ctx.getBean("car_arrays2", Car.class).describe(
					"car_arrays2 - declared after the 'spareEngines' bean definition; wired identically to car_arrays1");
			ctx.getBean("car_collections1", Car.class)
					.describe("car_collections1 - declared before the 'accessories' bean definition; "
							+ "still gets it wired, same reason as car_arrays1");
			ctx.getBean("car_collections2", Car.class).describe(
					"car_collections2 - declared after the 'accessories' bean definition; wired identically to car_collections1");
			ctx.getBean("car_uuid1", Car.class)
					.describe("car_uuid1 - vehicleId from the prototype UUID.randomUUID() bean");
			ctx.getBean("car_uuid2", Car.class)
					.describe("car_uuid2 - a DIFFERENT UUID: the only property in this file where "
							+ "the two cars genuinely differ, thanks to prototype scope");
		}
		System.out.println();

		System.out.println("\n=== 4) beans-byname-default-autowire.xml ===");
		System.out.println("============================================\n\n");
		try (ConfigurableApplicationContext ctx = new ClassPathXmlApplicationContext(
				"byName/carDekho/resources/beans-byname-default-autowire.xml")) {
			ctx.getBean("carInheritedDefault", Car.class).describe(
					"carInheritedDefault - no autowire attribute, inherits <beans default-autowire=\"byName\">");
			ctx.getBean("carExplicitDefault", Car.class)
					.describe("carExplicitDefault - autowire=\"default\", same effect as omitting it");
			ctx.getBean("carOptedOut", Car.class)
					.describe("carOptedOut - autowire=\"no\" overrides the container-wide default");
		}
		System.out.println();

	}

}
