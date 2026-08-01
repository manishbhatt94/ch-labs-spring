package constructor.carDekho;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.UnsatisfiedDependencyException;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import constructor.carDekho.car.BudgetCar;
import constructor.carDekho.car.Car;
import constructor.carDekho.car.EntryLevelCar;
import constructor.carDekho.car.FleetCar;

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

		System.out.println("\n=== 6) beans-constructor-explicit-arg-overrides.xml ===");
		System.out.println("========================================================\n\n");
		try (ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext(
				"constructor/carDekho/resources/beans-constructor-explicit-arg-overrides.xml")) {
			ctx.getBean("car_byIndex", Car.class).describe("car_byIndex - explicit constructor-arg by index");
			ctx.getBean("car_byType", Car.class).describe("car_byType - explicit constructor-arg by type");
			ctx.getBean("car_byName", Car.class).describe("car_byName - explicit constructor-arg by name");
			ctx.getBean("car_mixedExplicitAndAutowired", Car.class)
					.describe("car_mixedExplicitAndAutowired - engine explicit, transmission/gps autowired");
		}
		System.out.println();

		System.out.println("\n=== 7) beans-constructor-simple-type-excluded.xml ===");
		System.out.println("=====================================================\n\n");
		try (ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext(
				"constructor/carDekho/resources/beans-constructor-simple-type-excluded.xml")) {
			String carColor = ctx.getBean("carColor", String.class);
			System.out.println("'carColor' bean from context: " + carColor);
			String[] featuresArray = ctx.getBean("featuresArray", String[].class);
			System.out.println("'featuresArray' bean from context: " + Arrays.toString(featuresArray) + " (type: "
					+ featuresArray.getClass().getName() + ")");
			List<BigDecimal> tripDistancesList = ctx.getBean("tripDistancesList", List.class);
			System.out.println("'tripDistancesList' bean from context: " + tripDistancesList + " (type: "
					+ tripDistancesList.getClass().getName() + ")");
			tripDistancesList.forEach(distance -> {
				System.out.println("    Distance: " + distance + " (type: " + distance.getClass().getName() + ")");
			});
			BigDecimal[] tripDistancesArray = ctx.getBean("tripDistancesArray", BigDecimal[].class);
			System.out.println("'tripDistancesArray' bean from context: " + Arrays.toString(tripDistancesArray)
					+ " (type: " + tripDistancesArray.getClass().getName() + ")");
			System.out.println();

			ctx.getBean("myCar", BudgetCar.class).describe("Simple-Type Constructor Param Exclusion Demo");
		}
		System.out.println();

		System.out.println("\n=== 8) beans-constructor-aggregation.xml ===");
		System.out.println("============================================\n\n");
		try (ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext(
				"constructor/carDekho/resources/beans-constructor-aggregation.xml")) {
			ctx.getBean("fleetCar_allAutowired", FleetCar.class)
					.describe("fleetCar_allAutowired - array/List/Map/Set aggregation, all via autowiring");
			ctx.getBean("fleetCar_explicitSpareEngines", FleetCar.class)
					.describe("fleetCar_explicitSpareEngines - spareEngines explicit, rest autowired");
		}
		System.out.println();

	}

}
