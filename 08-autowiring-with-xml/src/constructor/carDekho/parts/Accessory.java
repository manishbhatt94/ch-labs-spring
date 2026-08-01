package constructor.carDekho.parts;

public class Accessory {

	private String name;

	public Accessory() {
	}

	public Accessory(String name) {
		this.name = name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "Accessory[name=" + name + "]";
	}

}
