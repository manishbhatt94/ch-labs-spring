package com.example.annodemo.mains;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.example.annodemo.config.BppConfig;

/**
 * @formatter:off
 * Expected order for bppTargetBean:
 *   -> bppTargetBean - constructor
 *   -> (Ordered:100) GammaPostProcessor.postProcessBeforeInitialization
 *   -> (Ordered:200) BetaPostProcessor.postProcessBeforeInitialization
 *   -> (Ordered:300) AlphaPostProcessor.postProcessBeforeInitialization
 *   -> AuditPostProcessor.postProcessBeforeInitialization
 *   -> OrderPostProcessor.postProcessBeforeInitialization
 *   -> bppTargetBean - @PostConstruct init()
 *   -> (Ordered:100) GammaPostProcessor.postProcessAfterInitialization
 *   -> (Ordered:200) BetaPostProcessor.postProcessAfterInitialization
 *   -> (Ordered:300) AlphaPostProcessor.postProcessAfterInitialization
 *   -> AuditPostProcessor.postProcessAfterInitialization
 *   -> OrderPostProcessor.postProcessAfterInitialization
 *
 *
 * BPPs that implement the org.springframework.core.Ordered interface, are
 * applied according to the integer order value they specify in the overridden
 * Ordered#getOrder (lower order value having the higher priority).
 *
 * In this example, below three BPPs implement the Ordered interface (in
 * decreasing priority):
 * - GammaPostProcessor (order value: 100)
 * - BetaPostProcessor  (order value: 200)
 * - AlphaPostProcessor (order value: 300)
 *
 *
 * Other BPPs that don't implement the Ordered interface) are applied in the
 * order Spring registered them - here, scan order.
 *
 * In this example, below two BPPs don't implement the Ordered interface:
 * - AuditPostProcessor
 * - OrderPostProcessor
 *
 * @formatter:on
 */
public class Main07_BeanPostProcessors {

	public static void main(String[] args) {

		System.out.println("=== Main07: BeanPostProcessor before/after wrapping ===");
		System.out.println("=======================================================\n");

		try (AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(BppConfig.class)) {
			System.out.println("\n--- context ready ---\n");
			printUserBeans(ctx);
		}
		System.out.println();

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
