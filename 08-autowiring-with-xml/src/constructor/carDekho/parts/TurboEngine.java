package constructor.carDekho.parts;

public class TurboEngine implements Engine {

	private String engineBeanId;

	@Override
	public String describe() {
		return "Turbo Engine (bean id = '" + engineBeanId + "')";
	}

	@Override
	public String toString() {
		return describe();
	}

	@Override
	public void setEngineBeanId(String engineBeanId) { // Setter
		this.engineBeanId = engineBeanId;
	}

}
