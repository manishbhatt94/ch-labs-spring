package di.beans.simplecomplex;

import org.springframework.stereotype.Component;

@Component
public class Subwoofer implements AudioDevice {

	@Override
	public String describe() {
		return "Subwoofer";
	}

}
