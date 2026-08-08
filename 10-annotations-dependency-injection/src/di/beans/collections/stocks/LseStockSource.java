package di.beans.collections.stocks;

import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;

@Component
public class LseStockSource implements StockSource, Ordered {

	@Override
	public String name() {
		return "LSE(Ordered=3)";
	}

	@Override
	public int getOrder() {
		return 3;
	}

}
