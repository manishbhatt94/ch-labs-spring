package constructor.carDekho.car;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;

import constructor.carDekho.parts.Engine;

public class BudgetCar {

	private final Engine engine;
	private final String color;
	private final String[] features;
	private final BigDecimal[] tripDistances;
	private final LocalDate[] serviceDates;

	public BudgetCar(Engine engine, String color, String[] features, BigDecimal[] tripDistances,
			LocalDate[] serviceDates) {
		System.out.println("[~~ CTOR called: BudgetCar(Engine engine, String color, String[] features,\n"
				+ "                         " + "BigDecimal[] tripDistances, LocalDate[] serviceDates). ~~]\n");
		this.engine = engine;
		this.color = color;
		this.features = features;
		this.tripDistances = tripDistances;
		this.serviceDates = serviceDates;
	}

	public void describe(String label) {
		System.out.println("--- " + label + " ---");
		System.out.println("  engine        : " + (engine != null ? engine : "null (NOT wired)"));
		System.out.println("  color         : " + (color != null ? color : "null (NOT wired)"));
		System.out.println("  features      : " + (features != null ? Arrays.toString(features) : "null (NOT wired)"));
		System.out.println(
				"  tripDistances : " + (tripDistances != null ? Arrays.toString(tripDistances) : "null (NOT wired)"));
		System.out.println(
				"  serviceDates  : " + (serviceDates != null ? Arrays.toString(serviceDates) : "null (NOT wired)"));
		System.out.println();
	}

}
