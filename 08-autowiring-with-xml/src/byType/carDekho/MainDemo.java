package byType.carDekho;

import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import byType.carDekho.car.Car;

public class MainDemo {

	public static void main(String[] args) {

		System.out.println("####### Auto-wiring mode \"byType\" Demo (XML config) ###########\n");

		System.out.println("\n=== 2a) beans-bytype-ambiguity-primary.xml ===");
		System.out.println("==============================================\n\n");
		try (ConfigurableApplicationContext ctx = new ClassPathXmlApplicationContext(
				"byType/carDekho/resources/beans-bytype-ambiguity-primary.xml")) {
			ctx.getBean("car_resolvedByPrimary", Car.class).describe(
					"car_resolvedByPrimary - ambiguous by type (2 Engine beans), resolved via primary=\"true\"; "
							+ "note spareEngines aggregates BOTH Engine beans regardless of which is primary");
			ctx.getBean("car_explicitOverridesPrimary", Car.class)
					.describe("car_explicitOverridesPrimary - explicit <property ref=\"enginePrimaryB\"/> "
							+ "overrides even primary-based autowiring");
		}

		System.out.println("\n=== 2b) beans-bytype-ambiguity-exclusion.xml ===");
		System.out.println("================================================\n\n");
		try (ConfigurableApplicationContext ctx = new ClassPathXmlApplicationContext(
				"byType/carDekho/resources/beans-bytype-ambiguity-exclusion.xml")) {
			ctx.getBean("car_resolvedByExclusion", Car.class)
					.describe("car_resolvedByExclusion - ambiguous by type (2 Engine beans), resolved because "
							+ "autowire-candidate=\"false\" removes engineExcludeA from candidacy entirely");
			ctx.getBean("car_explicitRefToExcludedEngine", Car.class)
					.describe("car_explicitRefToExcludedEngine - explicit ref still resolves the "
							+ "autowire-candidate=\"false\" bean, same as byName");
		}

	}

}
