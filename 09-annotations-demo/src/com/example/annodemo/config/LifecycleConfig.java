package com.example.annodemo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import com.example.annodemo.lifecycle.AllCallbacksPojo;
import com.example.annodemo.lifecycle.CustomInitDestroyPojo;

@Configuration
@ComponentScan("com.example.annodemo.lifecycle") // picks up the two @Component life-cycle beans
// i.e. InitializingDisposableBean & PostConstructPreDestroyBean.
public class LifecycleConfig {

	// @formatter:off
    // XML equivalent: <bean class="...CustomInitDestroyPojo"
    //                       init-method="init" destroy-method="destroy"/>
	// @formatter:on
	@Bean(initMethod = "init", destroyMethod = "destroy")
	public CustomInitDestroyPojo customInitDestroyPojo() {
		return new CustomInitDestroyPojo();
	}

	// @formatter:off
    // XML equivalent: <bean class="...AllCallbacksPojo"
    //                       init-method="customInit" destroy-method="customDestroy"/>
	// @formatter:on
	@Bean(initMethod = "customInit", destroyMethod = "customDestroy")
	public AllCallbacksPojo allCallbacksPojo() {
		return new AllCallbacksPojo();
	}

}
