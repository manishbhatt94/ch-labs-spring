package com.example.annodemo.mains;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.example.annodemo.config.LifecycleConfig;

public class Main06_LifecycleCallbackOrder {

	public static void main(String[] args) {

		System.out.println("=== Main06: lifecycle callback precedence order ===");
		System.out.println("===================================================\n");

		System.out.println("\n> Below two classes are annotated with @Component:");
		System.out.println("  1. InitializingDisposableBean");
		System.out.println("  2. PostConstructPreDestroyBean");
		System.out.println("\n> Below two typed objects are returned from @Bean methods in a @Configuration class:");
		System.out.println("  1. CustomInitDestroyPojo");
		System.out.println("  2. AllCallbacksPojo");

		System.out.println("\n- Watch AllCallbacksPojo's numbered lines below.\n");

		AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(LifecycleConfig.class);

		printUserBeans(ctx);

		System.out.println("\n--- context fully initialized, beans ready for use ---\n");

		System.out.println("\n--- Closing context now (triggers destroy callbacks) ...\n");
		ctx.close();

		System.out.println("\n--- Done with destroy callbacks. Program execution finished ---\n");

	}

	public static void printUserBeans(AnnotationConfigApplicationContext ctx) {

		System.out.println();
		System.out.println(">> Listing all user-defined beans in the context:");
		String[] beanNames = ctx.getBeanDefinitionNames();
		for (String name : beanNames) {
			if (name.startsWith("org.springframework")) {
				continue; // skip Spring internal beans
			}
			Object bean = ctx.getBean(name);
			System.out.println("   - " + name + " [ Class -> " + bean.getClass().getName() + " ]");
		}

	}

}
