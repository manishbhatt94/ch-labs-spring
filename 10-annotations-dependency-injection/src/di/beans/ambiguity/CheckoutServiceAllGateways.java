package di.beans.ambiguity;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import di.beans.ambiguity.payment.PaymentGateway;

/**
 * Sub-case 3a: same 3 PaymentGateway beans (one marked @Primary), but this time
 * injected into a COLLECTION-typed point. @Primary only disambiguates
 * SINGLE-valued injection points -- for a List, ambiguity resolution simply
 * does not apply, and ALL matching beans are aggregated regardless of which one
 * carries @Primary.
 */
@Component
public class CheckoutServiceAllGateways {

	@Autowired
	private List<PaymentGateway> allGateways;

	public void printStatus() {
		StringBuilder sb = new StringBuilder();
		for (PaymentGateway g : allGateways) {
			sb.append(g.describe()).append(" | ");
		}
		System.out.println("    [CheckoutServiceAllGateways] allGateways=" + sb
				+ " (expected: all 3, @Primary is irrelevant here)");
	}

}
