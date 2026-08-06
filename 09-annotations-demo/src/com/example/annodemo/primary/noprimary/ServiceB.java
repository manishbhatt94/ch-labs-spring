package com.example.annodemo.primary.noprimary;

import org.springframework.stereotype.Component;

/** No @Primary anywhere in this package -> genuinely ambiguous. */
@Component
public class ServiceB implements AmbiguousService {
}
