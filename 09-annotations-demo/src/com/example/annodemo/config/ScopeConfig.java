package com.example.annodemo.config;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import com.example.annodemo.instantiation.Widget;

@Configuration
@ComponentScan("com.example.annodemo.scope")
public class ScopeConfig {

	@Bean
	@Scope(scopeName = ConfigurableBeanFactory.SCOPE_SINGLETON)
	public Widget singletonWidgetBean() {
		System.out.println(
				"[scope] {! ScopeConfig#singletonWidgetBean() !}" + " -- singletonWidgetBean() @Bean method invoked");
		return new Widget("scope-config-class-bean-method-singleton");
	}

	@Bean
	@Scope(scopeName = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
	public Widget protoWidgetBean() {
		System.out
				.println("[scope] {! ScopeConfig#protoWidgetBean() !}" + " -- protoWidgetBean() @Bean method invoked");
		return new Widget("scope-config-class-bean-method-prototype");
	}

}
