package di.beans.valuespel;

/**
 * Deliberately mirrors the Inventor/PlaceOfBirth classes used in the official
 * Spring SpEL documentation, for continuity while reading it.
 */
public class Inventor {

	private String name;
	private String nationality;
	private City placeOfBirth; // left null on one instance -- safe navigation demo
	private String nickname; // never set -- Elvis operator demo
	private String[] inventions;
	private SimpleCalculator calc; // for SpEL method (safe) invocation demo with `calculator?.max(...)`

	public Inventor(String name, String nationality) {
		this.name = name;
		this.nationality = nationality;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getNationality() {
		return nationality;
	}

	public City getPlaceOfBirth() {
		return placeOfBirth;
	}

	public void setPlaceOfBirth(City placeOfBirth) {
		this.placeOfBirth = placeOfBirth;
	}

	public String getNickname() {
		return nickname;
	}

	public String[] getInventions() {
		return inventions;
	}

	public void setInventions(String[] inventions) {
		this.inventions = inventions;
	}

	public SimpleCalculator getCalc() {
		return calc;
	}

	public void setCalc(SimpleCalculator calc) {
		this.calc = calc;
	}

	public String describe() {
		return name + " (" + nationality + ")";
	}

}
