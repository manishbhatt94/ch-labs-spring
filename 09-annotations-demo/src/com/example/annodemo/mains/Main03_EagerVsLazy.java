package com.example.annodemo.mains;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.example.annodemo.config.DefaultLazyScanConfig;
import com.example.annodemo.config.PlainScanConfig;
import com.example.annodemo.lazyinit.EagerBean;
import com.example.annodemo.lazyinit.LazyBean;

public class Main03_EagerVsLazy {

	public static void main(String[] args) {

		System.out.println("=== Main03a: PlainScanConfig (only per-bean @Lazy applies) ===");
		System.out.println("==============================================================\n");
		try (AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(PlainScanConfig.class)) {
			System.out.println("\n-- context refreshed, beans above already constructed except LazyBean --\n");
			System.out.println("Now calling getBean(LazyBean.class) ...");
			ctx.getBean(LazyBean.class);
		}

		System.out.println("\n\n--=--=--=--=--=--=--=--=--=--=--=--=--=--=--=--=--=--=--=--=--\n");
		System.out.println("=== Main03b: DefaultLazyScanConfig ===");
		System.out.println("======================================\n");
		System.out.println("(@ComponentScan(lazyInit=true) = the real default-lazy-init equivalent for");
		System.out.println(" SCANNED @Component beans; class-level @Lazy is a SEPARATE mechanism that");
		System.out.println(" only defers @Bean METHODS declared in this same class - see demoWidgetBean)");
		System.out.println();
		try (AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(
				DefaultLazyScanConfig.class)) {
			System.out.println("\n-- context refreshed; only ForcedEagerBean should have printed above --\n");
			System.out.println("Now calling getBean(EagerBean.class) ...");
			ctx.getBean(EagerBean.class);
			System.out.println("Now calling getBean(LazyBean.class) ...");
			ctx.getBean(LazyBean.class);
			System.out.println("Now calling getBean(\"demoWidgetBean\") ...");
			ctx.getBean("demoWidgetBean");
		}

	}

}
