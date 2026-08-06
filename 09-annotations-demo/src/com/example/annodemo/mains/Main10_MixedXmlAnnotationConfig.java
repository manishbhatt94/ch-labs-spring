package com.example.annodemo.mains;

import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @formatter:off
 * Demonstrates: <context:annotation-config/> is required for @PostConstruct/
 * @PreDestroy (and @Autowired, @Value, etc.) to work on beans that were
 * declared the old-fashioned way, as plain <bean/> elements in XML.
 * @formatter:on
 */
public class Main10_MixedXmlAnnotationConfig {

	public static void main(String[] args) {

		System.out.println("=== Main10a: WITH <context:annotation-config/> ===");
		System.out.println("==================================================\n");
		try (ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext(
				"com/example/annodemo/mixedxml/beans-with-annotation-config.xml")) {
			System.out.println("-- expect to see '@PostConstruct fired!' above --");
		}

		System.out.println("\n");
		System.out.println("=== Main10b: WITHOUT <context:annotation-config/> ===");
		System.out.println("=====================================================\n");
		try (ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext(
				"com/example/annodemo/mixedxml/beans-without-annotation-config.xml")) {
			System.out.println("-- notice '@PostConstruct fired!' is MISSING this time --");
		}

		System.out.println("\n");
		System.out.println("=== Main10c: WITH <context:component-scan/> (finds @Component classes too) ===");
		System.out.println("==============================================================================\n");
		try (ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext(
				"com/example/annodemo/mixedxml/beans-with-component-scan.xml")) {

			System.out.println();
			System.out.println(">> Listing all user-defined beans in the context:");
			for (String name : ctx.getBeanDefinitionNames()) {
				if (name.startsWith("org.springframework")) {
					continue; // skip Spring internal infrastructure beans
				}
				System.out.println("   - " + name + " [ Class -> " + ctx.getBean(name).getClass().getName() + " ]");
			}
			System.out.println();
		}

		System.out.println();

	}

}
