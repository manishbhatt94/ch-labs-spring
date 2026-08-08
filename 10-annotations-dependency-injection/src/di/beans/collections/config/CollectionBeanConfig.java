package di.beans.collections.config;

import java.util.Arrays;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * A @Bean method whose RETURN TYPE is itself List<String>. This registers a
 * SINGLE bean of type List<String>. An injection point of that same type
 * receives this ONE bean directly -- this is plain single-bean injection, NOT
 * the aggregation-of-many-individually-registered-beans behavior shown
 * elsewhere in this package (contrast with NewsAggregatorUnordered /
 * WeatherAggregatorOrdered / StockAggregatorOrderedInterface above).
 */
@Configuration
public class CollectionBeanConfig {

	@Bean
	public List<String> preferredExchanges() {
		return Arrays.asList("NYSE", "NASDAQ");
	}

}
