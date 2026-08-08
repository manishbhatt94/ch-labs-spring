package di.beans.collections.stocks;

import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;

@Component
public class NasdaqStockSource implements StockSource, Ordered {

	@Override
	public String name() {
		return "NASDAQ(Ordered=2)";
	}

	@Override
	public int getOrder() {
		return 2;
	}

}
