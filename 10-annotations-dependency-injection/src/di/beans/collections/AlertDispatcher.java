package di.beans.collections;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import di.beans.collections.alerts.AlertChannel;

@Component
public class AlertDispatcher {

	// Check the AlertConfig class for the two @Bean methods that return
	// AlertChannel.
	@Autowired
	private List<AlertChannel> alertChannels;

	public void printStatus() {
		StringBuilder sb = new StringBuilder();
		for (AlertChannel c : alertChannels) {
			sb.append(c.getName()).append(" ");
		}
		System.out.println("    [AlertDispatcher] order=" + sb.toString().trim()
				+ "      (expected: Email SMS -- per-@Bean-method @Order)");
	}

}
