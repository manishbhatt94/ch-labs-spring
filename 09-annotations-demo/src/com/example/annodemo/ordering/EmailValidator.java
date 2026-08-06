package com.example.annodemo.ordering;

import org.springframework.stereotype.Component;

/**
 * No @Order at all -> falls back to registration order, not sorted numerically.
 */
@Component
public class EmailValidator implements Validator {

	@Override
	public String name() {
		return "EmailValidator(no @Order)";
	}

}
