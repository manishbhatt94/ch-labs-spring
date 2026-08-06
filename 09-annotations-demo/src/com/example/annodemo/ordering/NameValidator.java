package com.example.annodemo.ordering;

import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(1)
public class NameValidator implements Validator {

	@Override
	public String name() {
		return "NameValidator(@Order=1)";
	}

}
