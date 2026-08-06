package com.example.annodemo.mains;

import java.util.Map;

import org.springframework.beans.factory.NoUniqueBeanDefinitionException;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.example.annodemo.config.NoPrimaryConfig;
import com.example.annodemo.config.PrimaryConfig;
import com.example.annodemo.primary.Notifier;
import com.example.annodemo.primary.noprimary.AmbiguousService;

public class Main09_PrimaryAndQualifierDeclaration {

	public static void main(String[] args) {

		System.out.println("=== Main09a: @Primary resolves ambiguity ===");
		System.out.println("============================================\n");
		try (AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(PrimaryConfig.class)) {
			Notifier resolved = ctx.getBean(Notifier.class); // 3 candidates exist!
			System.out.println("ctx.getBean(Notifier.class) resolved to:    --> " + resolved.describe()
					+ "\n   (won by @Primary, no ambiguity error).");

			System.out.println("\nAll Notifier beans in context:");
			Map<String, Notifier> all = ctx.getBeansOfType(Notifier.class);
			all.forEach((name, bean) -> System.out.println("    " + name + " \t-> " + bean.describe()));
		}

		System.out.println("\n");
		System.out.println("=== Main09b: WITHOUT @Primary, ambiguity is a startup-time error ===");
		System.out.println("====================================================================\n");
		try (AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(NoPrimaryConfig.class)) {
			System.out.println("Attempting to call getBean(AmbiguousService.class) below:\n");
			ctx.getBean(AmbiguousService.class); // expect this to throw
			System.out.println("This line should NOT be reached.");
		} catch (NoUniqueBeanDefinitionException ex) {
			System.out.println("Caught expected NoUniqueBeanDefinitionException: \n  " + ex.getMessage());
		}

		System.out.println();

	}

}
