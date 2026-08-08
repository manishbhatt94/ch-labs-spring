package di.beans.collections.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;

import di.beans.collections.alerts.AlertChannel;

/**
 * Both @Bean methods here produce instances of the SAME class, AlertChannel. A
 * class-level @Order on AlertChannel itself could not distinguish between these
 * two separate bean definitions (one class, two beans), so @Order has to be
 * declared on the individual @Bean FACTORY METHODS instead.
 *
 * NOTE (per Spring docs): if @Order were placed on THIS @Configuration class
 * instead, it would only affect the evaluation order of configuration classes
 * relative to one another at startup -- it would NOT propagate to, or affect
 * the ordering of, the beans the @Bean methods below produce.
 */
@Configuration
public class AlertConfig {

	@Bean
	@Order(2)
	public AlertChannel smsAlert() {
		return new AlertChannel("SMS");
	}

	@Bean
	@Order(1)
	public AlertChannel emailAlert() {
		return new AlertChannel("Email");
	}

}
