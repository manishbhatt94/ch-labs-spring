package byType.carDekho.fleet;

import java.util.List;
import java.util.Map;
import java.util.Set;

import byType.carDekho.parts.Accessory;
import byType.carDekho.parts.Engine;

/**
 * Purpose-built to isolate byType's "aggregate every matching bean" behavior
 * from the rest of the Car-based demos - there's no ambiguity here and no
 * single-value resolution at all. Every property below is a multi-value type,
 * which byType (and constructor mode) treats completely differently from a
 * single Engine-typed property: instead of picking ONE candidate, it simply
 * collects ALL of them.
 */
public class EngineFleet {

	private List<Engine> engineList;
	private Set<Engine> engineSet;
	private Map<String, Engine> engineMap;
	private Set<Accessory> accessoryFleet;

	public void setEngineList(List<Engine> engineList) {
		this.engineList = engineList;
	}

	public void setEngineSet(Set<Engine> engineSet) {
		this.engineSet = engineSet;
	}

	public void setEngineMap(Map<String, Engine> engineMap) {
		this.engineMap = engineMap;
	}

	public void setAccessoryFleet(Set<Accessory> accessoryFleet) {
		this.accessoryFleet = accessoryFleet;
	}

	public void describe(String label) {
		System.out.println("---- " + label + " ----");
		System.out.println("  engineList     : " + (engineList == null ? "null (NOT wired)" : engineList));
		System.out.println("  engineSet      : " + (engineSet == null ? "null (NOT wired)" : engineSet));
		System.out.println("  engineMap      : " + (engineMap == null ? "null (NOT wired)" : engineMap));
		System.out.println("  accessoryFleet : " + (accessoryFleet == null ? "null (NOT wired)" : accessoryFleet));
		System.out.println();
	}

}
