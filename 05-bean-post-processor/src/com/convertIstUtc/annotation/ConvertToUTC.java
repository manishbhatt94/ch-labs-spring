package com.convertIstUtc.annotation;

import static java.lang.annotation.ElementType.FIELD;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

// Tells Java to make this annotation readable while the application is running
@Retention(RetentionPolicy.RUNTIME)
// Restricts this annotation so it can only be placed on instance variables/fields
@Target(FIELD)
public @interface ConvertToUTC {

}
