package com.example.annodemo.lifecycle;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.springframework.stereotype.Component;

/**
 * JSR-250 annotations. Requires CommonAnnotationBeanPostProcessor to be
 * registered, which happens AUTOMATICALLY as part of @ComponentScan /
 * AnnotationConfigApplicationContext (unlike XML, where you had to register
 * CommonAnnotationBeanPostProcessor by hand).
 */
@Component
public class PostConstructPreDestroyBean {

	public PostConstructPreDestroyBean() {
		System.out.println("[lifecycle] PostConstructPreDestroyBean: constructor");
	}

	@PostConstruct
	public void init() {
		System.out.println("[lifecycle] PostConstructPreDestroyBean: @PostConstruct init()");
	}

	@PreDestroy
	public void cleanup() {
		System.out.println("[lifecycle] PostConstructPreDestroyBean: @PreDestroy cleanup()");
	}

}
