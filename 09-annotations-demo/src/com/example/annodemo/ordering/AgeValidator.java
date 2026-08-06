package com.example.annodemo.ordering;

import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(2)
public class AgeValidator implements Validator {

	@Override
	public String name() {
		return "AgeValidator(@Order=2)";
	}

}
