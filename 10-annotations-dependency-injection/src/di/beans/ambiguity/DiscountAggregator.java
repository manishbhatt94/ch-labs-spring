package di.beans.ambiguity;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import di.beans.ambiguity.discount.DiscountRule;

/**
 * Sub-case 3c: 5 DiscountRule beans exist; 3 are tagged @Qualifier("seasonal"),
 * 2 are not tagged at all. Per the Spring docs, "Qualifiers also apply to typed
 * collections... all matching beans, according to the declared qualifiers, are
 * injected as a collection." So @Qualifier here acts as a FILTER over the
 * aggregation, not a single-value disambiguator.
 */
@Component
public class DiscountAggregator {

	@Autowired
	@Qualifier("seasonal")
	private List<DiscountRule> seasonalDiscounts;

	public void printStatus() {
		StringBuilder sb = new StringBuilder();
		for (DiscountRule d : seasonalDiscounts) {
			sb.append(d.describe()).append(" | ");
		}
		System.out.println("    [DiscountAggregator] seasonalDiscounts=" + sb
				+ " (expected: SummerSale, BlackFriday, NewYear only -- 2 of 5 filtered OUT)");
	}

}
