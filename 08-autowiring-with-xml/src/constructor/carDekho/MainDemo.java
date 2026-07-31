package constructor.carDekho;

import org.springframework.beans.factory.UnsatisfiedDependencyException;
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

		System.out.println("\n=== 3) beans-constructor-partial-match-forces-lesser-ctor.xml ===");
		System.out.println("=================================================================\n\n");
		try (ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext(
				"constructor/carDekho/resources/beans-constructor-partial-match-forces-lesser-ctor.xml")) {
			ctx.getBean("myCar", Car.class).describe("Partial Match Fallback Demo");
		}
		System.out.println();

		System.out.println("\n=== 4) beans-constructor-zero-match-single-ctor.xml ===");
		System.out.println("=======================================================\n\n");
		try (ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext(
				"constructor/carDekho/resources/beans-constructor-zero-match-single-ctor.xml")) {
			// We should never actually reach this line - the context above is
			// expected to fail to refresh before getBean() is even reachable.
			ctx.getBean("myCar", EntryLevelCar.class).describe("Zero Match on Single Constructor Demo");
		} catch (UnsatisfiedDependencyException ex) { // UnsatisfiedDependencyException caused by
														// NoSuchBeanDefinitionException
			System.out.println("\nContext refresh failed as expected -- exception below:");
			System.out.println("  " + ex.getClass().getSimpleName() + ": " + ex.getMessage());
		}
		System.out.println();

		System.out.println("\n=== 5a) beans-constructor-ambiguity-primary.xml ===");
		System.out.println("===================================================\n\n");
		try (ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext(
				"constructor/carDekho/resources/beans-constructor-ambiguity-primary.xml")) {
			ctx.getBean("car_resolvedByPrimary", EntryLevelCar.class)
					.describe("car_resolvedByPrimary - Ambiguity Resolved via primary Demo");
			ctx.getBean("car_explicitOverridesPrimary", EntryLevelCar.class).describe(
					"car_explicitOverridesPrimary - explicit <constructor-arg ref=\"economyEngine\"/> overrides even primary-based autowiring");
		}
		System.out.println();

		System.out.println("\n=== 5b) beans-constructor-ambiguity-exclusion.xml ===");
		System.out.println("======================================================\n\n");
		try (ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext(
				"constructor/carDekho/resources/beans-constructor-ambiguity-exclusion.xml")) {
			ctx.getBean("car_resolvedByExclusion", EntryLevelCar.class)
					.describe("car_resolvedByExclusion - ambiguous by type (2 Engine beans), "
							+ "resolved because autowire-candidate=\"false\" removes 'economyEngine' from candidacy entirely");
			ctx.getBean("car_explicitRefToExcludedEngine", EntryLevelCar.class)
					.describe("car_explicitRefToExcludedEngine - explicit ref still resolves to the excluded "
							+ "autowire-candidate=\"false\" ('economyEngine') bean");
		}
		System.out.println();

	}

}
