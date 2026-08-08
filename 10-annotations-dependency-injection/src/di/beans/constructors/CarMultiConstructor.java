package di.beans.constructors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Has TWO constructors and no single unambiguous default, so one of them MUST
 * be marked @Autowired to tell the container which one to use.
 */
@Component
public class CarMultiConstructor {

	private final Engine engine;

	public CarMultiConstructor() {
		this.engine = null;
		System.out.println(
				"⁜    [CarMultiConstructor] built via NO-ARG constructor " + "(this one should NOT be chosen)");
	}

	@Autowired
	public CarMultiConstructor(Engine engine) {
		this.engine = engine;
		System.out.println("⁜    [CarMultiConstructor] built via @Autowired(Engine) constructor "
				+ "-- this is the one Spring must choose");
	}

	@Override
	public String toString() {
		return "CarMultiConstructor [engine=" + engine + "]";
	}

}
