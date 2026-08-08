package di.beans.constructors;

import org.springframework.stereotype.Component;

@Component
public class Engine {
	public Engine() {
		System.out.println("⁜        (Engine instance created)");
	}
}
