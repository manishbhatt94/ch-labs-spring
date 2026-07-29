package byName.carDekho.parts;

public class GpsSystem {

	private GpsModuleType gpsModuleType;

	public void setGpsModuleType(GpsModuleType gpsModuleType) {
		this.gpsModuleType = gpsModuleType;
	}

	@Override
	public String toString() {
		return "GpsSystem [gpsModuleType=" + gpsModuleType + "]";
	}

}
