package di.main;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import di.beans.selfinjection.ReportGenerator;

/**
 * BONUS (low priority, intentionally shallow -- see README): self-injection
 * mechanism only, no real AOP/@Transactional proxy involved.
 */
public class Main07_SelfInjectionBonus {

	public static void main(String[] args) {

		System.out.println("=========================================================");
		System.out.println(" MAIN07 (BONUS): self-injection mechanism");
		System.out.println("=========================================================\n");

		AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext("di.beans.selfinjection");

		ReportGenerator contextBean = ctx.getBean(ReportGenerator.class);

		System.out.println();
		System.out.println("\n--- Section 1: calling a method directly (this) ---\n");
		contextBean.generateReport();

		System.out.println();
		System.out.println("\n--- Section 2: calling the SAME method via the self-injected field ---\n");
		contextBean.generateReportViaSelf();

		System.out.println();
		System.out.println("\n--- Section 3: identity check ---\n");
		System.out.println("    contextBean == contextBean.getSelfReference() ? "
				+ (contextBean == contextBean.getSelfReference())
				+ "\n      (expected: TRUE -- no AOP proxy is configured in this project, so 'self' is plain 'this')");
		System.out.println();
		System.out.println("    NOTE: in a real @Transactional/AOP-advised bean, the self-injected");
		System.out.println("    reference would instead be a PROXY, not the raw instance -- so calling");
		System.out.println("    self.someTransactionalMethod() would correctly trigger that advice,");
		System.out.println("    whereas this.someTransactionalMethod() would silently bypass it.");
		System.out.println("    This project does not configure real AOP proxying (out of scope);");
		System.out.println("    that is left for a future, dedicated AOP-focused learning phase.");

		ctx.close();
		System.out.println();

	}

}
