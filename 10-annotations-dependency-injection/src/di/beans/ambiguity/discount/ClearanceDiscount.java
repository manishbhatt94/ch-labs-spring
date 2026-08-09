package di.beans.ambiguity.discount;

import org.springframework.stereotype.Component;

/**
 * Deliberately NOT @Qualifier("seasonal") -- must be excluded from the filtered
 * collection injection demonstrated in DiscountAggregator.
 */
@Component
public class ClearanceDiscount implements DiscountRule {

	@Override
	public String describe() {
		return "Clearance";
	}

}
