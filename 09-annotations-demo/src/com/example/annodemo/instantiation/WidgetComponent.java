package com.example.annodemo.instantiation;

import org.springframework.stereotype.Component;

/**
 * XML equivalent: <bean class="com.x.WidgetComponent"/>
 * Plain constructor instantiation - Spring just calls `new WidgetComponent()`.
 */
@Component
public class WidgetComponent {
    public WidgetComponent() {
        System.out.println("[instantiation] WidgetComponent constructed directly (@Component)");
    }
}
