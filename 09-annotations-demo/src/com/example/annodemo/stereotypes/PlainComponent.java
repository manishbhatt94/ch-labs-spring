package com.example.annodemo.stereotypes;

import org.springframework.stereotype.Component;

/**
 * XML equivalent: <bean id="plainComponent" class="...PlainComponent"/>
 * Plain @Component is the generic stereotype - use when none of the
 * more specific stereotypes (@Service/@Repository/@Controller) apply.
 */
@Component
public class PlainComponent {
    public PlainComponent() {
        System.out.println("[stereotypes] PlainComponent constructed (@Component)");
    }
}
