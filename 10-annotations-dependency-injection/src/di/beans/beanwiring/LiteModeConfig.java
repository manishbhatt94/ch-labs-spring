package di.beans.beanwiring;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * proxyBeanMethods = false = "lite mode": no CGLIB subclass is generated, so an
 * in-class call to engine() is just a REGULAR Java method call -- it is NOT
 * intercepted, and creates a brand-new Engine2 every time it runs, rather than
 * reusing the container-managed singleton. Method-parameter injection is
 * unaffected either way, because it is resolved through the container's normal
 * autowiring machinery, not by intercepting an in-body method call.
 */
@Configuration(proxyBeanMethods = false)
public class LiteModeConfig {

	@Bean
	public Engine2 engine() {
		return new Engine2();
	}

	// Style 1 under LITE mode: expected to MISBEHAVE (separate instance)
	@Bean
	public CarStyleA carDirectCallLite() {
		return new CarStyleA(engine());
	}

	// Style 2 under LITE mode: expected to still be CORRECT
	@Bean
	public CarStyleB carParamInjectionLite(Engine2 engine) {
		return new CarStyleB(engine);
	}

}
