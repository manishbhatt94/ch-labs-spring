package di.beans.simplecomplex;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Array of a SIMPLE type: Strings can't be @Component-scanned, so an array of a
 * simple type has to be registered as ONE explicit @Bean of array type. The
 * injection point below receives that single array bean directly -- this is NOT
 * aggregation, it's ordinary single-bean injection where the bean's type just
 * happens to be an array.
 */
@Configuration
public class LocalizationConfig {

	@Bean
	public String[] supportedLanguages() {
		return new String[] { "en", "fr", "de" };
	}

}
