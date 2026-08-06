package com.example.annodemo.primary;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

/**
 * @formatter:off
 * Tags this bean with a qualifier value. On its own (with no @Autowired
 * consumer using @Qualifier("sms")) this doesn't change resolution -
 * @Qualifier only matters at the INJECTION POINT. It's declared here for
 * completeness; the actual "select by qualifier" demo belongs in the
 * separate dependency-injection project, since it requires @Autowired.
 * @formatter:on
 */
@Component
@Qualifier("sms")
public class SmsNotifier implements Notifier {

	@Override
	public String describe() {
		return "SmsNotifier   (@Qualifier(\"sms\"))";
	}

}
