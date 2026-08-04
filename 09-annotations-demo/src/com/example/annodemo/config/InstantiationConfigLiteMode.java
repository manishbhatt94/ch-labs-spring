package com.example.annodemo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.example.annodemo.instantiation.WidgetInstanceFactory;

/**
 * Corner case (Spring 5.2+): @Configuration(proxyBeanMethods = false) ("lite
 * mode") disables the CGLIB self-reference trick shown in InstantiationConfig.
 * Calling widgetInstanceFactory() from within anotherBeanThatCallsFactory() now
 * behaves like a PLAIN JAVA METHOD CALL - it runs the method body again and
 * constructs a brand-new WidgetInstanceFactory each time, breaking singleton
 * semantics for this internal call path. Compare the printed identityHash
 * values against InstantiationConfig's output to see the difference.
 */
@Configuration(proxyBeanMethods = false)
public class InstantiationConfigLiteMode {

	@Bean
	public WidgetInstanceFactory widgetInstanceFactory() {
		return new WidgetInstanceFactory();
	}

	@Bean
	public WidgetInstanceFactory anotherBeanThatCallsFactory() {
		// In lite mode this is just a normal Java call -> NEW instance,
		// NOT the singleton bean registered above.
		return widgetInstanceFactory();
	}

}
