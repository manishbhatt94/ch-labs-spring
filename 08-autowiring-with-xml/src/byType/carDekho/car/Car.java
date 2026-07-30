package byType.carDekho.car;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Set;
import java.util.UUID;

import byType.carDekho.parts.Accessory;
import byType.carDekho.parts.AutomaticTransmission;
import byType.carDekho.parts.Engine;
import byType.carDekho.parts.GpsSystem;

/**
 * Like byName, autowire="byType" only ever calls JavaBean setter methods - no
 * field injection, no constructors. Every property here is backed by a plain
 * setXxx method.
 */
public class Car {

	private Engine engine;
	private AutomaticTransmission transmission;
	private GpsSystem gps;
	private String color;
	private BigDecimal dealershipPrice;
	private String[] serviceHistory;
	private Engine[] spareEngines;
	private Set<Accessory> accessories;
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
