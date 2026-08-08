package di.beans.collections.stocks;

import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;

@Component
public class NyseStockSource implements StockSource, Ordered {

	@Override
	public String name() {
		return "NYSE(Ordered=1)";
	}

	@Override
	public int getOrder() {
		return 1;
	}

}
