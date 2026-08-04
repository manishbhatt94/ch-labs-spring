package com.example.annodemo.instantiation;

/**
 * XML equivalent:
 * <bean class="com.x.WidgetStaticFactory" factory-method="createWidget"/> A
 * plain static factory method - Spring never instantiates WidgetStaticFactory
 * itself, it just invokes the static method.
 */
public class WidgetStaticFactory {

	public static Widget createWidget() {
		System.out.println("[instantiation] {!= WidgetStaticFactory.createWidget() =!} -- "
				+ "WidgetStaticFactory.createWidget() invoked");
		return new Widget("static-factory-method");
	}

}
