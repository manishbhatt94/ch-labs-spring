package di.beans.ambiguity.shipping;

import org.springframework.stereotype.Component;

@Component("expressShipping")
public class ExpressShippingCalculator implements ShippingCalculator {

	@Override
	public String describe() {
		return "Express";
	}

}
