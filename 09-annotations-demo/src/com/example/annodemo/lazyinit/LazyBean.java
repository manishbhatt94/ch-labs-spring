package com.example.annodemo.lazyinit;

import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

/**
 * XML equivalent: <bean lazy-init="true"/>
 * Construction is deferred until the first getBean() lookup.
 */
@Component
@Lazy
public class LazyBean {
    public LazyBean() {
        System.out.println("[lazy] LazyBean constructed (only on first use!)");
    }
}
