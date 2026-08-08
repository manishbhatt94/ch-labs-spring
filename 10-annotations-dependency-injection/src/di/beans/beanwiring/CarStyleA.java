package di.beans.beanwiring;

/** Holder built via the DIRECT-METHOD-CALL wiring style. */
public class CarStyleA {

	private final Engine2 engine;

	public CarStyleA(Engine2 engine) {
		this.engine = engine;
	}

	public Engine2 getEngine() {
		return engine;
	}

}
