package di.beans.constructors;

import org.springframework.stereotype.Component;

@Component
public class Speedometer {

	public Speedometer() {
		System.out.println("⁜        (Speedometer instance created)");
	}

}
