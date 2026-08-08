package di.beans.collections;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import di.beans.collections.stocks.StockSource;
import di.beans.collections.weather.WeatherSource;

@Component
public class AggregateWeatherStockMapSet {

	@Autowired
	private Map<String, WeatherSource> weatherSources;

	@Autowired
	private Set<StockSource> exchanges;

	public void printStatus() {

		System.out.println(
				"    [AggregateWeatherStockMapSet] weatherSources (" + weatherSources.getClass().getName() + ")");
		System.out.println("    [AggregateWeatherStockMapSet] weatherSources=" + printWeatherSources());

		System.out.println();

		System.out.println("    [AggregateWeatherStockMapSet] exchanges (" + exchanges.getClass().getName() + ")");
		System.out.println("    [AggregateWeatherStockMapSet] exchanges=" + printExchanges());
	}

	public String printWeatherSources() {
		StringBuilder sb = new StringBuilder();
		sb.append("{ ");
		String[] keys = weatherSources.keySet().toArray(new String[0]);
		for (int i = 0; i < keys.length; i++) {
			String key = keys[i];
			WeatherSource source = weatherSources.get(key);
			sb.append(key).append(": ").append(source.name());
			if (i < keys.length - 1) {
				sb.append(", ");
			}
		}
		sb.append(" }");

		return sb.toString();
	}

	public String printExchanges() {
		List<String> names = exchanges.stream().map(StockSource::name).collect(Collectors.toList());
		return names.toString();
	}

}
