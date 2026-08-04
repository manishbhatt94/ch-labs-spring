package com.example.annodemo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;

import com.example.annodemo.instantiation.Widget;

/**
 * @formatter:off
 * ERRATUM (originally this class assumed class-level @Lazy on a
 * @Configuration class also deferred @ComponentScan-discovered @Component
 * beans - it does NOT). Per the official @Lazy Javadoc: "If Lazy is present
 * on a @Configuration class, this indicates that all @Bean methods within
 * that @Configuration should be lazily initialized." - it only reaches
 * @Bean METHODS declared directly in that same class, never beans
 * discovered by an accompanying @ComponentScan.
 *
 * The real annotation equivalent of XML's <beans default-lazy-init="true">
 * for SCANNED @Component beans is a dedicated, separate attribute:
 *     @ComponentScan(lazyInit = true)     (added in Spring 4.1)
 *
 * This class now demonstrates BOTH mechanisms side by side, since they are
 * independent and easy to conflate:
 *
 *   1) @ComponentScan(..., lazyInit = true) -> defers EagerBean/LazyBean
 *      (scanned @Component classes), same as XML's default-lazy-init.
 *   2) @Lazy on THIS class -> defers demoWidgetBean() below (a @Bean
 *      method declared here), completely independent of (1).
 *
 * Expectation when this config is used:
 *   - EagerBean       -> now DEFERRED, because of lazyInit=true on the scan
 *   - LazyBean         -> still deferred (its own @Lazy was already true)
 *   - ForcedEagerBean -> still constructed EAGERLY: its own @Lazy(false)
 *                        overrides the scan's lazyInit=true default, just
 *                        like XML's <bean lazy-init="false"/> can override
 *                        <beans default-lazy-init="true">
 *   - demoWidgetBean() -> ALSO deferred, but for the UNRELATED reason of
 *                        this class's own @Lazy annotation governing its
 *                        @Bean methods.
 * @formatter:on
 */
@Configuration
@ComponentScan(basePackages = "com.example.annodemo.lazyinit", lazyInit = true)
@Lazy
public class DefaultLazyScanConfig {

	// Proves that class-level @Lazy on @Configutation class, governs @Bean methods
	// declared here -
	// a completely separate mechanism from @ComponentScan(lazyInit=true).
	@Bean
	public Widget demoWidgetBean() {
		System.out.println("[lazy] demoWidgetBean() @Bean method invoked"
				+ " (deferred solely because of THIS class's own @Lazy annotation)");
		return new Widget("lazy-configuration-class-bean-method");
	}

}
