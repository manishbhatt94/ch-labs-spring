package com.example.annodemo.primary;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

/** XML equivalent: <bean primary="true"/> */
@Component
@Primary
public class EmailNotifier implements Notifier {

	@Override
	public String describe() {
		return "EmailNotifier (@Primary)";
	}

}
