package di.beans.ambiguity.payment;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

@Component
@Primary
public class CreditCardGateway implements PaymentGateway {

	@Override
	public String describe() {
		return "CreditCard(@Primary)";
	}

}
