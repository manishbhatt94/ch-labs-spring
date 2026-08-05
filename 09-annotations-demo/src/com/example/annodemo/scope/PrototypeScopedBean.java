package com.example.annodemo.scope;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * XML equivalent: <bean scope="prototype"/> A brand-new instance is created on
 * every getBean() call. (String literal "prototype" works too - the constant is
 * just clearer.)
 */
@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
// Or mention the string "prototype" in place instead of using the String member
// `String SCOPE_PROTOTYPE = "prototype";` declared in ConfigurableBeanFactory interface.
// @Scope("prototype")
public class PrototypeScopedBean {

	public PrototypeScopedBean() {
		super();
		System.out.println(
				"[scope] {! PrototypeScopedBean#PrototypeScopedBean() !}" + " -- PrototypeScopedBean constructed");
	}

}
