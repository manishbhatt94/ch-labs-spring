package com.example.annodemo.primary;

import org.springframework.stereotype.Component;

/** Plain candidate, no @Primary, no @Qualifier. */
@Component
public class PushNotifier implements Notifier {

	@Override
	public String describe() {
		return "PushNotifier  (plain)";
	}

}
