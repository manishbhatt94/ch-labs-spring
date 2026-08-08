package di.beans.beanwiring;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Default @Configuration = "full mode": Spring CGLIB-subclasses this class, so
 * an @Bean method call FROM WITHIN the class (like engine() below) is
 * intercepted and redirected to the container, returning the cached singleton
 * instead of re-running the method body. Both wiring styles below are therefore
 * safe here.
 */
@Configuration
public class FullModeConfig {

	@Bean
	public Engine2 engine() {
		return new Engine2();
	}

	// Style 1: DIRECT METHOD CALL used to express the inter-bean dependency
	@Bean
	public CarStyleA carDirectCall() {
		return new CarStyleA(engine());
	}

	// Style 2: METHOD-PARAMETER injection used to express the same dependency
	@Bean
	public CarStyleB carParamInjection(Engine2 engine) {
		return new CarStyleB(engine);
	}

}
