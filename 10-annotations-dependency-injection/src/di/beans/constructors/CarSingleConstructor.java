package di.beans.constructors;

import org.springframework.stereotype.Component;

/**
 * Has exactly ONE constructor. Since Spring 4.3, when a bean class declares
 * only a single constructor, Spring implicitly treats it as the constructor to
 * autowire -- no @Autowired annotation is required at all.
 */
@Component
public class CarSingleConstructor {

	private final Engine engine;

	public CarSingleConstructor(Engine engine) {
		this.engine = engine;
		System.out.println(
				"⁜    [CarSingleConstructor] built via its ONLY constructor (Engine) " + "-- no @Autowired needed");
	}

	@Override
	public String toString() {
		return "CarSingleConstructor [engine=" + engine + "]";
	}

}
