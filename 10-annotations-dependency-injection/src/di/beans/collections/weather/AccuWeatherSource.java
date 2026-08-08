package di.beans.collections.weather;

import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(2)
public class AccuWeatherSource implements WeatherSource {

	@Override
	public String name() {
		return "AccuWeather(@Order=2)";
	}

}
