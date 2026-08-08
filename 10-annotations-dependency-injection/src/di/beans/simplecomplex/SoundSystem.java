package di.beans.simplecomplex;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Complex/reference type autowiring -- contrast with ThemeSettings' @Value
 * simple-type injection. Only ONE Amplifier bean exists here, so this stays
 * unambiguous (ambiguity RESOLUTION itself is Main04's dedicated topic).
 */
@Component
public class SoundSystem {

	@Autowired
	private Amplifier amplifier;

	public void printStatus() {
		System.out.println("    [SoundSystem] amplifier=" + (amplifier != null ? "OK" : "NULL"));
	}

}
