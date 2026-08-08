package di.beans.simplecomplex;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * Simple/primitive-ish injection via @Value using literal values. Full
 * property-file / SpEL treatment is Main06's job -- this is just a preview
 * contrast against the complex/reference-type injection below.
 */
@Component
public class ThemeSettings {

	@Value("Dark")
	private String themeName;

	@Value("42")
	private int volumeLevel;

	public void printStatus() {
		System.out.println("    [ThemeSettings] themeName=" + themeName + ", volumeLevel=" + volumeLevel);
	}

}
