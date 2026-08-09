package di.beans.optional;

import org.springframework.stereotype.Component;

@Component
public class GoldLoyaltyProgram implements LoyaltyProgram {

	@Override
	public String describe() {
		return "Gold";
	}

}
