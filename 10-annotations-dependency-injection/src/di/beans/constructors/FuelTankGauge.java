package di.beans.constructors;

import org.springframework.stereotype.Component;

@Component
public class FuelTankGauge {

	public FuelTankGauge() {
		System.out.println("⁜        (FuelTankGauge instance created)");
	}

}
