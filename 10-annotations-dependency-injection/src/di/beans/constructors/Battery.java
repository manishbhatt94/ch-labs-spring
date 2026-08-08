package di.beans.constructors;

import org.springframework.stereotype.Component;

@Component
public class Battery {
	public Battery() {
		System.out.println("⁜        (Battery instance created)");
	}
}
