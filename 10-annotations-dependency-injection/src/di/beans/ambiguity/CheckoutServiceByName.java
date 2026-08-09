package di.beans.ambiguity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import di.beans.ambiguity.shipping.ShippingCalculator;

/**
 * @formatter:off
 * Single-valued injection point, 2 ShippingCalculator candidates, no
 * @Primary and no @Qualifier -> resolved by the IMPLICIT BY-NAME fallback:
 * the field name "expressShipping" matches the bean name "expressShipping"
 * (registered via @Component("expressShipping")).
 * @formatter:on
 */
@Component
public class CheckoutServiceByName {

	@Autowired
	private ShippingCalculator expressShipping;

	public void printStatus() {
		System.out.println("    [CheckoutServiceByName] expressShipping=" + expressShipping.describe()
				+ "  (expected: Express, via implicit by-name match, no @Primary/@Qualifier used)");
	}

}
