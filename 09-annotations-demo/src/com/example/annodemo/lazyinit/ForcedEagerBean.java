package com.example.annodemo.lazyinit;

import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

/**
 * Corner case: @Lazy(false) explicitly forces EAGER init.
 * Pointless on its own (eager is already the default), but essential
 * when a @Configuration class sets a *class-level* @Lazy default
 * (XML equivalent: <beans default-lazy-init="true">) - this bean-level
 * override lets one bean opt back OUT of that class-wide default,
 * exactly like a single <bean lazy-init="false"/> could override
 * <beans default-lazy-init="true">.
 */
@Component
@Lazy(false)
public class ForcedEagerBean {
    public ForcedEagerBean() {
        System.out.println("[lazy] ForcedEagerBean constructed (@Lazy(false) override)");
    }
}
