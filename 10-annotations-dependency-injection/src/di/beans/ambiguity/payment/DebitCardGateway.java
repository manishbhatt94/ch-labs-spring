package di.beans.ambiguity.payment;

import org.springframework.stereotype.Component;

@Component
public class DebitCardGateway implements PaymentGateway {

	@Override
	public String describe() {
		return "DebitCard";
	}

}
