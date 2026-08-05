package com.example.annodemo.scope;

import org.springframework.stereotype.Component;

/**
 * No @Scope -> singleton is the default, identical to XML's implicit default.
 */
@Component
// "singleton" scope is the default, and is not required to be declared using @Scope.
// But we can explicitly add the @Scope annotation for "singleton", although
// redundant, because of the default value already being "singleton" scope:
//@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
// Or mention the string "singleton" in place instead of using the String member
// `String SCOPE_SINGLETON = "singleton";` declared in ConfigurableBeanFactory interface.
//@Scope("singleton")
public class SingletonScopedBean {

	public SingletonScopedBean() {
		super();
		System.out.println(
				"[scope] {! SingletonScopedBean#SingletonScopedBean() !}" + " -- SingletonScopedBean constructed");
	}

}
