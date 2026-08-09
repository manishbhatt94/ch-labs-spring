package di.main;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import di.beans.ambiguity.CheckoutServiceAllGateways;
import di.beans.ambiguity.CheckoutServiceByName;
import di.beans.ambiguity.CheckoutServicePrimary;
import di.beans.ambiguity.CheckoutServiceQualifier;
import di.beans.ambiguity.DiscountAggregator;
import di.beans.ambiguityxml.FraudCheckServiceUser;
import di.beans.ambiguityxml.XmlQualifierConfig;

public class Main04_AmbiguityResolution {

	public static void main(String[] args) {

		System.out.println("==================================================================");
		System.out.println(" MAIN04: ambiguity resolution (single-value AND collection cases)");
		System.out.println("==================================================================\n");

		AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext("di.beans.ambiguity");
		// note: this component-scan intentionally does NOT include the
		// di.beans.ambiguityxml sibling package -- that one is wired
		// up separately below via XmlQualifierConfig, to keep the XML+
		// annotation mixing self-contained.

		System.out.println();
		System.out.println("\n--- Section 1: single-value disambiguation via @Primary ---\n");
		ctx.getBean(CheckoutServicePrimary.class).printStatus();

		System.out.println();
		System.out.println("\n--- Section 2: single-value disambiguation via @Qualifier ---\n");
		ctx.getBean(CheckoutServiceQualifier.class).printStatus();

		System.out.println();
		System.out.println("\n--- Section 3: single-value disambiguation via implicit by-name fallback ---\n");
		ctx.getBean(CheckoutServiceByName.class).printStatus();

		System.out.println();
		System.out.println("\n--- Section 3a: collection injection ignores @Primary/@Qualifier entirely ---\n");
		ctx.getBean(CheckoutServiceAllGateways.class).printStatus();

		System.out.println();
		System.out.println("\n--- Section 3c: @Qualifier as a COLLECTION FILTER (3 of 5 beans match) ---\n");
		ctx.getBean(DiscountAggregator.class).printStatus();

		ctx.close();
		System.out.println();

		System.out.println();
		System.out.println("\n--- Section 3b: XML <qualifier> + Java-side @Qualifier, single-valued ---\n");
		AnnotationConfigApplicationContext xmlCtx = new AnnotationConfigApplicationContext(XmlQualifierConfig.class);
		xmlCtx.getBean(FraudCheckServiceUser.class).printStatus();

		xmlCtx.close();
		System.out.println();

	}

}
