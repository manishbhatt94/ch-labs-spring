package com.example.annodemo.mains;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.example.annodemo.config.StereotypeConfig;

/**
 * Demonstrates: @Component / @Service / @Repository / @Controller all being
 * discovered and registered via a single @ComponentScan.
 */
public class Main01_Stereotypes_ComponentScan {

	public static void main(String[] args) {

		System.out.println("=== Main01: Stereotypes + @ComponentScan ===");
		System.out.println("============================================\n");
		try (AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(StereotypeConfig.class)) {

			System.out.println("\nRegistered bean names found by scanning:");
			for (String name : ctx.getBeanDefinitionNames()) {
				System.out.println("  -> " + name);
			}

		}

	}

}
