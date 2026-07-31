package constructor.carDekho.car;

import constructor.carDekho.parts.AutomaticTransmission;
import constructor.carDekho.parts.Engine;
import constructor.carDekho.parts.GpsSystem;

public class Car {

	private final Engine engine;
	private final AutomaticTransmission transmission;
	private GpsSystem gps;

	// Ctor A: 2 dependencies
	public Car(Engine engine, AutomaticTransmission transmission) {
		System.out.println("[~~ CTOR called: Car(Engine engine, AutomaticTransmission transmission). ~~]\n");
		this.engine = engine;
		this.transmission = transmission;
	}

	// Ctor B: 3 dependencies -- the "greediest" satisfiable constructor
	// when a GpsSystem bean is also present in the container.
	public Car(Engine engine, AutomaticTransmission transmission, GpsSystem gps) {
		System.out.println("[~~ CTOR called: Car(Engine engine, AutomaticTransmission transmission, GpsSystem gps)\n");
		this.engine = engine;
		this.transmission = transmission;
		this.gps = gps;
	}

	public void describe(String label) {
		System.out.println("--- " + label + " ---");
		System.out.println("Engine: " + (engine != null ? engine : "null (NOT wired)"));
		System.out.println("Transmission: " + (transmission != null ? transmission : "null (NOT wired)"));
		System.out.println("GPS: " + (gps != null ? gps : "null (NOT wired)"));
		System.out.println();
	}

}
