package com.example.annodemo.lifecycle;

/**
 * @formatter:off
 * Deliberately a PLAIN POJO with no Spring annotations/interfaces at all -
 * this is the case XML's init-method/destroy-method attributes exist for
 * (wiring life-cycle callbacks onto a class you don't want to couple to
 * Spring APIs).
 * Wired up via @Bean(initMethod=..., destroyMethod=...)
 * in LifecycleConfig - see that class.
 * @formatter:on
 */
public class CustomInitDestroyPojo {

	public CustomInitDestroyPojo() {
		System.out.println("[lifecycle] CustomInitDestroyPojo: constructor");
	}

	public void init() {
		System.out.println("[lifecycle] CustomInitDestroyPojo: custom init() (via @Bean initMethod)");
	}

	public void destroy() {
		System.out.println("[lifecycle] CustomInitDestroyPojo: custom destroy() (via @Bean destroyMethod)");
	}

}
