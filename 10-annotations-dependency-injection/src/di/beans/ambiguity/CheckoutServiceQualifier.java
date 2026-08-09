package di.beans.ambiguity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import di.beans.ambiguity.notification.NotificationService;

/**
 * @formatter:off
 * Single-valued injection point, 2 NotificationService candidates, neither
 * @Primary -> resolved via @Qualifier declared on BOTH the bean and here.
 * @formatter:on
 **/
@Component
public class CheckoutServiceQualifier {

	@Autowired
	@Qualifier("email")
	private NotificationService notifier;

	public void printStatus() {
		System.out.println("    [CheckoutServiceQualifier] notifier=" + notifier.describe()
				+ "  (expected: Email, via matching @Qualifier values)");
	}

}
