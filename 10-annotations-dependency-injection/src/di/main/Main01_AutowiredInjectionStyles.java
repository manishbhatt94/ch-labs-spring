package di.main;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import di.beans.constructors.CarFlexibleConstructor;
import di.beans.constructors.CarMultiConstructor;
import di.beans.constructors.CarSingleConstructor;
import di.beans.constructors.Dashboard;

public class Main01_AutowiredInjectionStyles {

	public static void main(String[] args) {

		System.out.println("=========================================================");
		System.out.println(" MAIN01: @Autowired injection styles + constructor rules");
		System.out.println("=========================================================\n");

		AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext("di.beans.constructors");

		System.out.println();
		System.out.println("\n--- Section 1: single-constructor class (implicit autowiring) ---\n");
		System.out.println(ctx.getBean("carSingleConstructor", CarSingleConstructor.class));

		System.out.println();
		System.out.println("\n--- Section 2: multi-constructor class, one @Autowired ---\n");
		System.out.println(ctx.getBean("carMultiConstructor", CarMultiConstructor.class));

		System.out.println();
		System.out.println("\n--- Section 3: multi-constructor class, both required=false ---\n");
		System.out.println(ctx.getBean("carFlexibleConstructor", CarFlexibleConstructor.class));

		System.out.println();
		System.out.println("\n--- Section 4: field / setter / arbitrary-method injection ---\n");
		Dashboard dashboard = ctx.getBean(Dashboard.class);
		dashboard.printStatus();
		System.out.println(dashboard);

		ctx.close();
		System.out.println();

	}

}
