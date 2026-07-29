package byName.carDekho.parts;

public class Accessory {

	private final String label;

	public Accessory(String label) {
		this.label = label;
	}

	@Override
	public String toString() {
		return "Accessory[" + label + "]";
	}

}
