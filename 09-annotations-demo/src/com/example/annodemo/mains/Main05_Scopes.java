package com.example.annodemo.mains;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.example.annodemo.config.ScopeConfig;
import com.example.annodemo.scope.PrototypeScopedBean;
import com.example.annodemo.scope.SingletonScopedBean;

public class Main05_Scopes {

	public static void main(String[] args) {

		System.out.println("=== Main05: singleton vs prototype scope ===");
		System.out.println("============================================\n");
		try (AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(ScopeConfig.class)) {

			System.out.println("\n-- context refreshed, singleton bean(s) have been constructed --\n\n");

			System.out.println(">> Calling getBean(SingletonScopedBean.class) twice -- "
					+ "Note that constructor WON'T be called at all.\n");
			SingletonScopedBean s1 = ctx.getBean(SingletonScopedBean.class);
			SingletonScopedBean s2 = ctx.getBean(SingletonScopedBean.class);
			System.out.println("> Singleton: s1==s2 ? " + (s1 == s2) + "  (expect true).\n");

			System.out.println();

			System.out.println(">> Calling getBean(PrototypeScopedBean.class) twice -- "
					+ "Note that constructor GETS CALLED twice.\n");
			PrototypeScopedBean p1 = ctx.getBean(PrototypeScopedBean.class);
			PrototypeScopedBean p2 = ctx.getBean(PrototypeScopedBean.class);
			System.out.println("\n> Prototype: p1==p2 ? " + (p1 == p2) + "  (expect false).\n");

		}

	}

}
