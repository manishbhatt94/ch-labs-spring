package di.beans.collections;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ExchangePreferences {

	// Check the CollectionBeanConfig class for the @Bean method
	// preferredExchanges() that returns a List<String> directly.
	@Autowired
	private List<String> preferredExchanges;

	public void printStatus() {
		System.out.println("    [ExchangePreferences] preferredExchanges=" + preferredExchanges
				+ "      (this is the ONE @Bean-returned List, not an aggregation)");
	}

}
