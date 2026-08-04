package com.example.annodemo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.example.annodemo.instantiation.Widget;
import com.example.annodemo.instantiation.WidgetInstanceFactory;
import com.example.annodemo.instantiation.WidgetStaticFactory;

/**
 * Default mode: @Configuration with proxyBeanMethods = true (the default).
 * Spring CGLIB-proxies this class so that calling widgetInstanceFactory() from
 * inside widgetFromInstanceFactory() returns the SAME cached singleton bean
 * instead of a fresh "new WidgetInstanceFactory()" - this is the
 * "self-reference through the proxy" trick.
 *
 * This lets us build the "instance factory method" style WITHOUT ever writing
 * an @Autowired parameter to obtain the factory bean.
 */
@Configuration
public class InstantiationConfig {

	// ---- style 2: static factory method ----
	// XML: factory-method="createWidget" (no factory-bean attribute)
	@Bean
	public Widget widgetFromStaticFactory() {
		return WidgetStaticFactory.createWidget();
	}

	// ---- style 3: instance factory method ----
	// XML: factory-bean="widgetInstanceFactory" factory-method="createWidget"
	@Bean
	public WidgetInstanceFactory widgetInstanceFactory() {
		return new WidgetInstanceFactory();
	}

	@Bean
	public Widget widgetFromInstanceFactory() {
		// Calling the sibling @Bean method directly - proxyBeanMethods=true
		// (the default) makes this resolve to the SAME singleton bean
		// rather than constructing a brand-new WidgetInstanceFactory.
		return widgetInstanceFactory().createWidget();
	}

}
