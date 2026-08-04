package com.example.annodemo.filters.excluded;

import org.springframework.stereotype.Repository;

/**
 * IS annotated with @Repository, so it would normally be picked up by
 * component-scan - but FilterConfig explicitly excludes every
 * @Repository-annotated class via excludeFilters. Expectation: this
 * bean is NEVER registered despite carrying a valid stereotype annotation.
 */
@Repository
public class RealRepository {
    public RealRepository() {
        System.out.println("[filters] RealRepository constructed - THIS SHOULD NOT PRINT!");
    }
}
