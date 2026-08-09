package di.beans.ambiguity.discount;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
@Qualifier("seasonal")
public class BlackFridayDiscount implements DiscountRule {

	@Override
	public String describe() {
		return "BlackFriday";
	}

}
