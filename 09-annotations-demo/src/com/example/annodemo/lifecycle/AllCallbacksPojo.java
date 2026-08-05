package com.example.annodemo.lifecycle;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;

/**
 * @formatter:off
 * THE key corner case: combines ALL FOUR lifecycle mechanisms on one bean
 * to reveal Spring's documented precedence order ("Combining Lifecycle
 * Mechanisms" in the reference docs):
 *
 *   INIT order    : @PostConstruct -> afterPropertiesSet()  -> custom init-method
 *   DESTROY order : @PreDestroy    -> destroy()             -> custom destroy-method
 *
 * Registered via @Bean(initMethod="customInit", destroyMethod="customDestroy")
 * in LifecycleConfig so the "custom init/destroy method" leg is exercised too.
 * @formatter:on
 */
public class AllCallbacksPojo implements InitializingBean, DisposableBean {

	public AllCallbacksPojo() {
		System.out.println("[lifecycle] AllCallbacksPojo: 1) constructor");
	}

	@PostConstruct
	public void postConstruct() {
		System.out.println("[lifecycle] AllCallbacksPojo: 2) @PostConstruct");
	}

	@Override
	public void afterPropertiesSet() {
		System.out.println("[lifecycle] AllCallbacksPojo: 3) InitializingBean#afterPropertiesSet()");
	}

	public void customInit() {
		System.out.println("[lifecycle] AllCallbacksPojo: 4) custom init-method (customInit)");
	}

	@PreDestroy
	public void preDestroy() {
		System.out.println("[lifecycle] AllCallbacksPojo: 1) @PreDestroy");
	}

	@Override
	public void destroy() {
		System.out.println("[lifecycle] AllCallbacksPojo: 2) DisposableBean#destroy()");
	}

	public void customDestroy() {
		System.out.println("[lifecycle] AllCallbacksPojo: 3) custom destroy-method (customDestroy)");
	}

}
