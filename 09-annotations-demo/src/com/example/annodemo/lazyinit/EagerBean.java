package com.example.annodemo.lazyinit;

import org.springframework.stereotype.Component;

/**
 * No @Lazy present -> eager by default under ApplicationContext,
 * same default as XML's <bean/> without lazy-init="true".
 */
@Component
public class EagerBean {
    public EagerBean() {
        System.out.println("[lazy] EagerBean constructed");
    }
}
