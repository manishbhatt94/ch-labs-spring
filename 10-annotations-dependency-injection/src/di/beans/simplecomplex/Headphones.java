package di.beans.simplecomplex;

import org.springframework.stereotype.Component;

@Component
public class Headphones implements AudioDevice {

	@Override
	public String describe() {
		return "Headphones";
	}

}
