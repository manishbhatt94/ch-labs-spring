package di.main;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import di.beans.beanwiring.CarStyleA;
import di.beans.beanwiring.CarStyleB;
import di.beans.beanwiring.Engine2;
import di.beans.beanwiring.FullModeConfig;
import di.beans.beanwiring.LiteModeConfig;
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

		System.out.println();
		System.out.println("\n--- Section 4a: @Bean-to-@Bean wiring under FULL mode (both styles safe) ---\n");
		AnnotationConfigApplicationContext fullCtx = new AnnotationConfigApplicationContext(FullModeConfig.class);
		Engine2 containerEngine = fullCtx.getBean(Engine2.class);
		CarStyleA directCall = fullCtx.getBean(CarStyleA.class);
		CarStyleB paramInjection = fullCtx.getBean(CarStyleB.class);
		System.out.println();
		System.out.println(
				"    containerEngine == directCall.getEngine()     ? " + (containerEngine == directCall.getEngine()));
		System.out.println("    containerEngine == paramInjection.getEngine() ? "
				+ (containerEngine == paramInjection.getEngine()));
		System.out.println("\n    → (expected: both true -- full mode keeps everything singleton-consistent)");
		fullCtx.close();

		System.out.println();
		System.out.println("\n--- Section 4b: @Bean-to-@Bean wiring under LITE mode (direct call breaks) ---\n");
		AnnotationConfigApplicationContext liteCtx = new AnnotationConfigApplicationContext(LiteModeConfig.class);
		Engine2 liteContainerEngine = liteCtx.getBean(Engine2.class);
		CarStyleA directCallLite = liteCtx.getBean("carDirectCallLite", CarStyleA.class);
		CarStyleB paramInjectionLite = liteCtx.getBean("carParamInjectionLite", CarStyleB.class);
		System.out.println();
		System.out.println("    liteContainerEngine == directCallLite.getEngine()     ? "
				+ (liteContainerEngine == directCallLite.getEngine())
				+ "\n    → (expected: FALSE -- direct call bypassed the container)\n");
		System.out.println("    liteContainerEngine == paramInjectionLite.getEngine() ? "
				+ (liteContainerEngine == paramInjectionLite.getEngine())
				+ "\n    → (expected: TRUE -- parameter injection unaffected by lite mode)");
		liteCtx.close();

		System.out.println();

	}

}
