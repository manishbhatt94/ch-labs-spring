package di.beans.simplecomplex;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Array of a COMPLEX (bean) type: Spring aggregates ALL AudioDevice beans into
 * the array automatically. This is a multi-valued injection point, so having 3
 * candidate beans here is NOT an ambiguity error (unlike a single-valued
 * AudioDevice field would be).
 */
@Component
public class AudioDeviceRouter {

	@Autowired
	private AudioDevice[] devices;

	public void printStatus() {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < devices.length; i++) {
			sb.append(devices[i].describe());
			if (i < devices.length - 1) {
				sb.append(", ");
			}
		}
		System.out.println("    [AudioDeviceRouter] devices=[" + sb + "] (count=" + devices.length + ")");
	}

}
