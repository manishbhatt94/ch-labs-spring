package com.example.annodemo.instantiation;

/**
 * XML equivalent (the "factory-bean" half):
 * <bean id="widgetInstanceFactory" class="com.x.WidgetInstanceFactory"/> This
 * class itself IS a bean, and one of its instance methods acts as the factory
 * method for a *different* bean (Widget).
 */
public class WidgetInstanceFactory {

	public WidgetInstanceFactory() {
		System.out.println("[instantiation] {!= WidgetInstanceFactory#WidgetInstanceFactory() =!} -- "
				+ "WidgetInstanceFactory constructed" + " (identityHash=" + System.identityHashCode(this) + ")");
	}

	public Widget createWidget() {
		System.out.println("[instantiation] {!= WidgetInstanceFactory.createWidget() =!} -- "
				+ "WidgetInstanceFactory#createWidget() invoked");
		return new Widget("instance-factory-method");
	}

}
