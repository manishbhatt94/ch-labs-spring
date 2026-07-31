package constructor.carDekho.car;

import constructor.carDekho.parts.AutomaticTransmission;
import constructor.carDekho.parts.Engine;

public class EntryLevelCar {

	private final Engine engine;
	private final AutomaticTransmission transmission;

	public EntryLevelCar(Engine engine, AutomaticTransmission transmission) {
		System.out.println("[~~ CTOR called: EntryLevelCar(Engine engine, AutomaticTransmission transmission). ~~]\n");
		this.engine = engine;
		this.transmission = transmission;
	}

	public void describe(String label) {
		System.out.println("--- " + label + " ---");
		System.out.println("  engine       : " + (engine != null ? engine : "null (NOT wired)"));
		System.out.println("  transmission : " + (transmission != null ? transmission : "null (NOT wired)"));
		System.out.println();
	}

}
