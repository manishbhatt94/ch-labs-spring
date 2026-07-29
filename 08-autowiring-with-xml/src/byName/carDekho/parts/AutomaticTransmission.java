package byName.carDekho.parts;

public class AutomaticTransmission {

	private String transmissionBeanId;

	@Override
	public String toString() {
		return "Automatic Transmission (bean id = '" + transmissionBeanId + "')";
	}

	public void setTransmissionBeanId(String transmissionBeanId) {
		this.transmissionBeanId = transmissionBeanId;
	}

}
