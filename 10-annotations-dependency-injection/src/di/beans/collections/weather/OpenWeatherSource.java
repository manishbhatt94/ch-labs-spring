package di.beans.collections.weather;

import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(1)
public class OpenWeatherSource implements WeatherSource {

	@Override
	public String name() {
		return "OpenWeather(@Order=1)";
	}

}
