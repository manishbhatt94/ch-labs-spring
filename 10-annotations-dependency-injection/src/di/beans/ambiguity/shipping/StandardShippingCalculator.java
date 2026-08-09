package di.beans.ambiguity.shipping;

import org.springframework.stereotype.Component;

@Component("standardShipping")
public class StandardShippingCalculator implements ShippingCalculator {

	@Override
	public String describe() {
		return "Standard";
	}

}
