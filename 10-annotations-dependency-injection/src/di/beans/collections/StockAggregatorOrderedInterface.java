package di.beans.collections;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import di.beans.collections.stocks.StockSource;

/**
 * Ordering here comes from each bean implementing org.springframework.core.
 * Ordered directly, instead of using the @Order annotation. Expected: NYSE(1),
 * NASDAQ(2), LSE(3).
 */
@Component
public class StockAggregatorOrderedInterface {

	@Autowired
	private List<StockSource> sources;

	public void printStatus() {
		StringBuilder sb = new StringBuilder();
		for (StockSource s : sources) {
			sb.append(s.name()).append(" | ");
		}
		System.out.println("    [StockAggregatorOrderedInterface] order=" + sb);
	}

}
