package com.example.annodemo.filters.included;

/**
 * Deliberately NOT annotated with @Component / @Repository.
 * It is picked up ONLY because of the custom REGEX includeFilter
 * in FilterConfig, matching classes whose name contains "Stub".
 */
public class StubRepositoryLike {
    public StubRepositoryLike() {
        System.out.println("[filters] StubRepositoryLike constructed (included via REGEX includeFilter)");
    }
}
