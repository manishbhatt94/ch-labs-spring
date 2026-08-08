package di.main;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import di.beans.simplecomplex.AudioDeviceRouter;
import di.beans.simplecomplex.LocalizationInfo;
import di.beans.simplecomplex.SoundSystem;
import di.beans.simplecomplex.ThemeSettings;

public class Main02_SimpleComplexArraysAndBeanWiring {

	public static void main(String[] args) {

		System.out.println("================================================================");
		System.out.println(" MAIN02: simple vs complex types, arrays, @Bean-to-@Bean wiring");
		System.out.println("================================================================\n");

		AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext();
		ctx.scan("di.beans.simplecomplex");
		ctx.refresh();

		System.out.println();
		System.out.println("\n--- Section 1: @Value simple type vs @Autowired complex type ---\n");
		ctx.getBean(ThemeSettings.class).printStatus();
		ctx.getBean(SoundSystem.class).printStatus();

		System.out.println();
		System.out.println("\n--- Section 2: array of a complex type (aggregated from 3 beans) ---\n");
		ctx.getBean(AudioDeviceRouter.class).printStatus();

		System.out.println();
		System.out.println("\n--- Section 3: array of a simple type (single @Bean, NOT aggregated) ---\n");
		ctx.getBean(LocalizationInfo.class).printStatus();

		ctx.close();
		System.out.println();

	}

}
