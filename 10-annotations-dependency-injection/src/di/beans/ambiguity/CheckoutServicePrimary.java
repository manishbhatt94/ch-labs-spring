package di.beans.ambiguity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import di.beans.ambiguity.payment.PaymentGateway;

/**
 * Single-valued injection point, 3 PaymentGateway candidates -> resolved
 * via @Primary.
 */
@Component
public class CheckoutServicePrimary {

	@Autowired
	private PaymentGateway gateway;

	public void printStatus() {
		System.out.println("    [CheckoutServicePrimary] gateway=" + gateway.describe()
				+ "  (expected: CreditCard, via @Primary)");
	}

}
