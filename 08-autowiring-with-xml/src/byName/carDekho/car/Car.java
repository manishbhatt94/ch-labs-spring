package byName.carDekho.car;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Set;
import java.util.UUID;

import byName.carDekho.parts.Accessory;
import byName.carDekho.parts.AutomaticTransmission;
import byName.carDekho.parts.Engine;
import byName.carDekho.parts.GpsSystem;

/**
 * IMPORTANT: with autowire="byName" (and also "byType"), Spring only ever calls
 * JavaBean SETTER methods to inject a match. There is no field injection and no
 * constructor injection involved in either of these two XML autowire modes -
 * that is exclusive to autowire="constructor" (for constructors) or
 * to @Autowired (which can target fields, constructors, and setters). Every
 * property below is therefore backed by a public setXxx method; that's what the
 * container's introspection is looking for.
 */
public class Car {

	// ---- wired as single objects by matching bean id == property name ----
	private Engine engine;
	private AutomaticTransmission transmission;
	private GpsSystem gps;

	// ---- "simple" types: NEVER autowire candidates, by design, no matter
	// what a same-named bean's type is ----
	private String color;
	private BigDecimal dealershipPrice;

	// ---- array of a SIMPLE type: still excluded, same rule as above ----
	private String[] serviceHistory;

	// ---- array of a COMPLEX type: NOT excluded, so byName CAN wire it if
	// a same-named array bean exists (see README for the nuance) ----
	private Engine[] spareEngines;

	// ---- collection of a COMPLEX type: also NOT excluded; byName wires it
	// the same way it wires anything else - by matching a bean of
	// that exact name, not by aggregating same-type beans (that
	// aggregation trick is specific to byType/constructor mode) ----
	private Set<Accessory> accessories;

	// ---- UUID via a prototype bean using a static factory method ----
	private UUID vehicleId;

	public void setEngine(Engine engine) {
		this.engine = engine;
	}

	public void setTransmission(AutomaticTransmission transmission) {
		this.transmission = transmission;
	}

	public void setGps(GpsSystem gps) {
		this.gps = gps;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public void setDealershipPrice(BigDecimal dealershipPrice) {
		this.dealershipPrice = dealershipPrice;
	}

	public void setServiceHistory(String[] serviceHistory) {
		this.serviceHistory = serviceHistory;
	}

	public void setSpareEngines(Engine[] spareEngines) {
		this.spareEngines = spareEngines;
	}

	public void setAccessories(Set<Accessory> accessories) {
		this.accessories = accessories;
	}

	public void setVehicleId(UUID vehicleId) {
		this.vehicleId = vehicleId;
	}

	public void describe(String label) {
		System.out.println("---- " + label + " ----");
		System.out.println("  engine          : " + (engine == null ? "null (NOT wired)" : engine.describe()));
		System.out.println("  transmission    : " + (transmission == null ? "null (NOT wired)" : transmission));
		System.out.println("  gps             : " + (gps == null ? "null (NOT wired)" : gps));
		System.out.println("  color           : " + (color == null ? "null (NOT wired)" : color));
		System.out.println("  dealershipPrice : " + (dealershipPrice == null ? "null (NOT wired)" : dealershipPrice));
		System.out.println("  serviceHistory  : "
				+ (serviceHistory == null ? "null (NOT wired)" : Arrays.toString(serviceHistory)));
		System.out.println(
				"  spareEngines    : " + (spareEngines == null ? "null (NOT wired)" : Arrays.toString(spareEngines)));
		System.out.println("  accessories     : " + (accessories == null ? "null (NOT wired)" : accessories));
		System.out.println("  vehicleId       : " + (vehicleId == null ? "null (NOT wired)" : vehicleId));
		System.out.println();
	}

}
