package di.beans.collections.weather;

import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(3)
public class NoaaWeatherSource implements WeatherSource {

	@Override
	public String name() {
		return "NOAA(@Order=3)";
	}

}
