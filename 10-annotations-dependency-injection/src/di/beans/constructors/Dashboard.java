package di.beans.constructors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Demonstrates the remaining three @Autowired injection styles: - field
 * injection - traditional setter injection - an arbitrary custom method (not
 * named like a setter, multiple args)
 */
@Component
public class Dashboard {

	// 'gps' field injected via @Autowired on the arbitrary-named method, i.e.
	// wireUpEverything(Engine, GPS), defined below.
	private GPS gps;

	// 'battery' field injected via traditional setter injection, i.e.
	// setBattery(Battery) setter method is annotated with @Autowired.
	private Battery battery;

	// 'engine' field injected via @Autowired on the same arbitrary-named method
	// that injects 'gps' above.
	private Engine engine;

	// 'fuelTankGauge' field is set using field injection (@Autowired on the field
	// itself). No setter or constructor needed. Set by Spring using Reflection API.
	@Autowired
	private FuelTankGauge fuelTankGauge;

	// No @Autowired here, or otherwise on any constructor/setter/method that
	// initializes this "speedometer" field - So this will remain as: null.
	private Speedometer speedometer;

	@Autowired
	public void setBattery(Battery battery) { // traditional setter injection
		this.battery = battery;
		System.out.println("⁜    [Dashboard] setBattery(Battery) called -- setter injection");
	}

	@Autowired
	public void wireUpEverything(Engine engine, GPS gps) { // arbitrary-named, multi-arg method
		this.engine = engine;
		this.gps = gps;
		System.out.println("⁜    [Dashboard] wireUpEverything(Engine, GPS) called "
				+ "-- arbitrary-named multi-arg method injection");
	}

	public void printStatus() {
		// @formatter:off
		System.out.println(
				"    [Dashboard] {\n"
				+ "        gps=" + (gps != null ? "OK" : "NULL") + ",\n"
				+ "        battery=" + (battery != null ? "OK" : "NULL") + ",\n"
				+ "        engine=" + (engine != null ? "OK" : "NULL") + ",\n"
				+ "        fuelTankGauge=" + (fuelTankGauge != null ? "OK" : "NULL") + ",\n"
				+ "        speedometer=" + (speedometer != null ? "OK" : "NULL") + "\n"
				+ "    }\n");
		// @formatter:on
	}

	@Override
	public String toString() {
		return "Dashboard [gps=" + gps + ", battery=" + battery + ", engine=" + engine + ", fuelTankGauge="
				+ fuelTankGauge + ", speedometer=" + speedometer + "]";
	}

}
