package di.beans.collections;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import di.beans.collections.weather.WeatherSource;

/**
 * Class-level @Order on each WeatherSource @Component determines the
 * aggregation order here. Expected: OpenWeather(1), AccuWeather(2), NOAA(3).
 */
@Component
public class WeatherAggregatorOrdered {

	@Autowired
	private List<WeatherSource> sources;

	public void printStatus() {
		StringBuilder sb = new StringBuilder();
		for (WeatherSource s : sources) {
			sb.append(s.name()).append(" | ");
		}
		System.out.println("    [WeatherAggregatorOrdered] order=" + sb);
	}

}
