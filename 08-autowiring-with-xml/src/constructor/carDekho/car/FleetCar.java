package constructor.carDekho.car;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;

import constructor.carDekho.parts.Accessory;
import constructor.carDekho.parts.Engine;

public class FleetCar {

	private final Engine primaryEngine;
	private final Engine[] spareEngines;
	private final List<Engine> engineList;
	private final Map<String, Engine> engineMap;
	private final Set<Accessory> accessories;

	public FleetCar(Engine primaryEngine, Engine[] spareEngines, List<Engine> engineList, Map<String, Engine> engineMap,
			Set<Accessory> accessories) {
		this.primaryEngine = primaryEngine;
		this.spareEngines = spareEngines;
		this.engineList = engineList;
		this.engineMap = engineMap;
		this.accessories = accessories;
	}

	public void describe(String label) {
		System.out.println("--- " + label + " ---");
		System.out.println("  primaryEngine : " + (primaryEngine != null ? primaryEngine : "null (NOT wired)"));
		System.out.println(
				"  spareEngines  : " + (spareEngines != null ? Arrays.toString(spareEngines) : "null (NOT wired)"));
		System.out.println("  engineList    : " + (engineList != null ? engineList : "null (NOT wired)"));
		System.out.println("  engineMap     : " + (engineMap != null ? engineMap : "null (NOT wired)"));
		System.out.println("  accessories   : " + (accessories != null ? accessories : "null (NOT wired)"));
		System.out.println();
	}

}
