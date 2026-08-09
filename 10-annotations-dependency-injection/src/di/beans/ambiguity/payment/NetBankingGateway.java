package di.beans.ambiguity.payment;

import org.springframework.stereotype.Component;

@Component
public class NetBankingGateway implements PaymentGateway {

	@Override
	public String describe() {
		return "NetBanking";
	}

}
