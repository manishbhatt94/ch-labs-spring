package com.example.annodemo.filters.included;

/**
 * Also NOT annotated, and its name does NOT match the "Stub" regex.
 * Expectation: this class is NEVER registered as a bean, proving that
 * plain component-scan (even with an includeFilter active) still
 * requires either @Component-family annotations OR an explicit filter
 * match - it does not blindly register every class in the package.
 */
public class PlainNote {
    public PlainNote() {
        System.out.println("[filters] PlainNote constructed - THIS SHOULD NOT PRINT!");
    }
}
