package com.example.annodemo.mains;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.example.annodemo.config.InstantiationConfig;
import com.example.annodemo.config.InstantiationConfigLiteMode;
import com.example.annodemo.instantiation.Widget;
import com.example.annodemo.instantiation.WidgetInstanceFactory;

public class Main04_InstantiationMethods {

	public static void main(String[] args) {

		System.out.println(
				"=== Main04a: constructor / static-factory / instance-factory (proxyBeanMethods=true, default) ===");
		System.out.println("############################################################");
		System.out.println("# PART A - proxyBeanMethods = true (the default, \"full\" mode)");
		System.out.println("############################################################\n");
		try (AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(
				InstantiationConfig.class)) {

			printUserBeans(ctx);

			System.out.println();
			Class<? extends InstantiationConfig> proxiedConfigClazz = ctx.getBean(InstantiationConfig.class).getClass();
			System.out.println(">> @Configuration(proxyBeanMethods = true) class InstantiationConfig "
					+ "-> CGLIB proxy class =\n   --> " + proxiedConfigClazz.getName());

			System.out.println();
			System.out.println(">> Fetching bean 'widgetFromStaticFactory' (built via a plain static factory method)");
			Widget viaStatic = ctx.getBean("widgetFromStaticFactory", Widget.class);
			System.out.println("   -> origin = " + viaStatic.getOrigin());

			System.out.println();
			System.out.println(">> Fetching bean 'widgetFromInstanceFactory' "
					+ "(built by calling widgetInstanceFactory().createWidget()");
			System.out.println(
					"   from WITHIN another @Bean method - watch: NO extra 'WidgetInstanceFactory constructed'");
			System.out.println("   line appears above, because the CGLIB proxy redirected that call to the SAME");
			System.out.println("   already-registered singleton instead of re-running the method body.)");
			Widget viaInstance = ctx.getBean("widgetFromInstanceFactory", Widget.class);
			System.out.println("   -> origin = " + viaInstance.getOrigin());

			System.out.println();
			System.out.println(">> Proof the factory itself stayed a true singleton across both direct-lookup");
			System.out.println("   and in-code self-call:");
			WidgetInstanceFactory looked_up = ctx.getBean("widgetInstanceFactory", WidgetInstanceFactory.class);
			System.out.println("   identityHash of the registered 'widgetInstanceFactory' bean = "
					+ System.identityHashCode(looked_up) + "\n"
					+ "   which matches the identityHash printed in the 'WidgetInstanceFactory constructed' line above.");

		}

		System.out.println("\n\n");
		System.out.println("--=--=--=--=--=--=--=--=--=--=--=--=--=--=--=--=--=--=--=--=--=--=--\n\n");
		System.out.println(
				"=== Main04b: proxyBeanMethods=false (\"lite mode\") - self-call is a PLAIN java call now ===");
		System.out.println("############################################################");
		System.out.println("# PART B - proxyBeanMethods = false (\"lite\" mode)");
		System.out.println("############################################################\n");
		try (AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(
				InstantiationConfigLiteMode.class)) {

			printUserBeans(ctx);

			System.out.println();
			Class<? extends InstantiationConfigLiteMode> nonProxiedConfigClazz = ctx
					.getBean(InstantiationConfigLiteMode.class).getClass();
			System.out.println(">> @Configuration(proxyBeanMethods = false) class InstantiationConfigLiteMode "
					+ "-> NON-CGLIB proxy class =\n   --> " + nonProxiedConfigClazz.getName());

			System.out.println();
			System.out.println(">> Notice TWO separate 'WidgetInstanceFactory constructed' lines printed above:");
			System.out.println("   1st = the container building the registered 'widgetInstanceFactory' bean");
			System.out.println("   2nd = 'anotherBeanThatCallsFactory' calling widgetInstanceFactory() as a");
			System.out.println("         PLAIN Java method (no interception in lite mode) -> re-runs the body");
			System.out.println("         -> builds a brand-new, UNTRACKED WidgetInstanceFactory.");

			WidgetInstanceFactory registeredBean = ctx.getBean("widgetInstanceFactory", WidgetInstanceFactory.class);
			WidgetInstanceFactory viaSelfCall = ctx.getBean("anotherBeanThatCallsFactory", WidgetInstanceFactory.class);

			System.out.println();
			System.out.println(">> registeredBean  identityHash = " + System.identityHashCode(registeredBean));
			System.out.println(">> viaSelfCall     identityHash = " + System.identityHashCode(viaSelfCall));
			System.out.println(">> same object?    " + (registeredBean == viaSelfCall)
					+ "   <-- singleton guarantee is BROKEN for this call path under lite mode");

		}

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
