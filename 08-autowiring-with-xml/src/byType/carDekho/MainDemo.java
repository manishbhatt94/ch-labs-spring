package byType.carDekho;

import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import byType.carDekho.car.Car;

public class MainDemo {

	public static void main(String[] args) {

		System.out.println("####### Auto-wiring mode \"byType\" Demo (XML config) ###########\n");

		System.out.println("\n=== 1) beans-bytype-basics.xml ===");
		System.out.println("==================================\n\n");
		try (ConfigurableApplicationContext ctx = new ClassPathXmlApplicationContext(
				"byType/carDekho/resources/beans-bytype-basics.xml")) {
			ctx.getBean("car_basic", Car.class)
					.describe("car_basic - single unambiguous candidate per type wired automatically; "
							+ "spareEngines picks up the same 'engine' instance as a 1-element array; "
							+ "color/dealershipPrice/serviceHistory stay null despite matching-typed beans existing");
			ctx.getBean("car_simpleTypesExplicit", Car.class)
					.describe("car_simpleTypesExplicit - color/dealershipPrice/serviceHistory set explicitly, "
							+ "the only way to populate a simple type or an array of one");
		}
		System.out.println();

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
		System.out.println();

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
		System.out.println();

		System.out.println("\n=== 3) beans-bytype-default-candidates.xml ===");
		System.out.println("==============================================\n\n");
		try (ConfigurableApplicationContext ctx = new ClassPathXmlApplicationContext(
				"byType/carDekho/resources/beans-bytype-default-candidates.xml")) {
			ctx.getBean("car_patternResolves", Car.class)
					.describe("car_patternResolves - 'engine' resolved because 'engineExcludedByPattern' doesn't match "
							+ "default-autowire-candidates=\"engine,transmission\"; 'gps' stays null because "
							+ "'gps' doesn't match the pattern either, leaving zero eligible candidates");
			ctx.getBean("car_explicitRefBypassesPattern", Car.class)
					.describe("car_explicitRefBypassesPattern - explicit refs still reach both pattern-excluded beans "
							+ "('engineExcludedByPattern' and 'gps')");
		}
		System.out.println();

	}

}
