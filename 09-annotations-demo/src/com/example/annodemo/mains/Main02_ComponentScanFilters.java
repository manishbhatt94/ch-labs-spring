package com.example.annodemo.mains;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.example.annodemo.config.FilterConfig;

/**
 * Demonstrates includeFilters / excludeFilters on @ComponentScan. Expected
 * console output: ONLY StubRepositoryLike's constructor message should print.
 * PlainNote and RealRepository must NOT print anything.
 */
public class Main02_ComponentScanFilters {

	public static void main(String[] args) {

		System.out.println("=== Main02: @ComponentScan include/exclude filters ===");
		System.out.println("======================================================\n");
		try (AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(FilterConfig.class)) {

			System.out.println("\nRegistered bean names:");
			for (String name : ctx.getBeanDefinitionNames()) {
				if (!name.toLowerCase().startsWith("org.springframework")) {
					System.out.println("  -> " + name);
				}
			}
		}

	}

}
