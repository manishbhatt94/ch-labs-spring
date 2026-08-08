package di.beans.beanwiring;

/** Holder built via the METHOD-PARAMETER-INJECTION wiring style. */
public class CarStyleB {

	private final Engine2 engine;

	public CarStyleB(Engine2 engine) {
		this.engine = engine;
	}

	public Engine2 getEngine() {
		return engine;
	}

}
