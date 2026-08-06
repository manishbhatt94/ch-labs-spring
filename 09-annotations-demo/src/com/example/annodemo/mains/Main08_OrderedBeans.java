package com.example.annodemo.mains;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.ObjectProvider;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.example.annodemo.config.OrderingConfig;
import com.example.annodemo.ordering.Validator;

/**
 * Demonstrates @Order without touching @Autowired: ObjectProvider's
 * orderedStream() applies the exact same AnnotationAwareOrderComparator logic
 * that field/constructor collection injection uses internally.
 */
public class Main08_OrderedBeans {

	public static void main(String[] args) {

		System.out.println("=== Main08: ordering beans in a collection via @Order ===");
		System.out.println("=========================================================\n");

		try (AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(OrderingConfig.class)) {

			ObjectProvider<Validator> provider = ctx.getBeanProvider(Validator.class);
			List<String> ordered = provider.orderedStream().map(Validator::name).collect(Collectors.toList());

			System.out.println("orderedStream() result:");
			ordered.forEach(name -> System.out.println("  -> " + name));

			System.out.println("\n(NameValidator@1, AgeValidator@2 sort first;"
					+ " EmailValidator with no @Order trails behind, in registration order)");
			System.out.println();

		}

	}

}
