package di.beans.constructors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Two constructors, BOTH marked @Autowired(required = false). Per the Spring
 * docs: "the constructor with the greatest number of dependencies that can be
 * satisfied by matching beans in the Spring container will be chosen." Both
 * Engine and Battery beans exist in this project, so the two-arg constructor is
 * expected to win.
 */
@Component
public class CarFlexibleConstructor {

	private final Engine engine;
	private final Battery battery;

	@Autowired(required = false)
	public CarFlexibleConstructor(Engine engine) {
		this.engine = engine;
		this.battery = null;
		System.out.println("⁜    [CarFlexibleConstructor] built via (Engine)-only constructor");
	}

	@Autowired(required = false)
	public CarFlexibleConstructor(Engine engine, Battery battery) {
		this.engine = engine;
		this.battery = battery;
		System.out.println("⁜    [CarFlexibleConstructor] built via (Engine, Battery) constructor "
				+ "-- expected winner: satisfies MORE dependencies");
	}

	@Override
	public String toString() {
		return "CarFlexibleConstructor [engine=" + engine + ", battery=" + battery + "]";
	}

}
