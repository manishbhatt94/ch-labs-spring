package di.main;

import org.springframework.beans.factory.UnsatisfiedDependencyException;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import di.beans.optional.NotifierClient;
import di.beans.optional.NullableDemoBean;
import di.beans.optional.OptionalDemoBean;
import di.beans.optional.RequiredCollectionConsumer;

public class Main05_OptionalDependencies {

	public static void main(String[] args) {

		System.out.println("=========================================================");
		System.out.println(" MAIN05: required semantics, Optional<T>, and @Nullable");
		System.out.println("=========================================================\n");

		System.out.println();
		System.out.println("\n--- Section 1: required=false on a setter (dependency absent) ---\n");
		AnnotationConfigApplicationContext ctx1 = new AnnotationConfigApplicationContext();
		ctx1.register(NotifierClient.class);
		ctx1.refresh();
		ctx1.getBean(NotifierClient.class).printStatus();
		ctx1.close();

		System.out.println();
		System.out.println("\n--- Section 2: default required=true on an EMPTY collection (expected FAILURE) ---\n");
		try {
			AnnotationConfigApplicationContext ctx2 = new AnnotationConfigApplicationContext();
			ctx2.register(RequiredCollectionConsumer.class);
			ctx2.refresh();
			System.out.println("    UNEXPECTED: context started successfully (should have failed)");
			ctx2.close();
		} catch (UnsatisfiedDependencyException e) {
			System.out.println("    Got expected UnsatisfiedDependencyException.");
			System.out.println("    Message: " + e.getMessage());
		}

		System.out.println();
		System.out.println("\n--- Section 3: Optional<T> on FIELD, SETTER, and CONSTRUCTOR PARAMETER ---\n");
		// Component-scanning the whole package picks up OptionalDemoBean,
		// NullableDemoBean, AND LoyaltyProgramGold (so one Optional below
		// resolves present, the other two resolve empty) in one context.
		AnnotationConfigApplicationContext ctx3 = new AnnotationConfigApplicationContext();
		ctx3.scan("di.beans.optional");
		ctx3.refresh();
		ctx3.getBean(OptionalDemoBean.class).printStatus();

		System.out.println();
		System.out.println("\n--- Section 4: @Nullable on a method parameter ---\n");
		ctx3.getBean(NullableDemoBean.class).printStatus();
		ctx3.close();

		System.out.println();

	}

}
