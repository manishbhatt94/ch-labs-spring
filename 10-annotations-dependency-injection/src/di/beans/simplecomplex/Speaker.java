package di.beans.simplecomplex;

import org.springframework.stereotype.Component;

@Component
public class Speaker implements AudioDevice {

	@Override
	public String describe() {
		return "Speaker";
	}

}
