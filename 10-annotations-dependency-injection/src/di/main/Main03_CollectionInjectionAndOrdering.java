package di.main;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import di.beans.collections.AggregateWeatherStockMapSet;
import di.beans.collections.AlertDispatcher;
import di.beans.collections.ExchangePreferences;
import di.beans.collections.NewsAggregatorUnordered;
import di.beans.collections.StockAggregatorOrderedInterface;
import di.beans.collections.WeatherAggregatorOrdered;

public class Main03_CollectionInjectionAndOrdering {

	public static void main(String[] args) {

		System.out.println("=========================================================");
		System.out.println(" MAIN03: collection injection, aggregation, and ordering");
		System.out.println("=========================================================\n");

		AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext("di.beans.collections");

		System.out.println("\n--- Section 1: aggregation with NATURAL (unordered) registration order ---\n");
		ctx.getBean(NewsAggregatorUnordered.class).printStatus();

		System.out.println();
		System.out.println("\n--- Section 2a: aggregation ordered via @Order ---\n");
		ctx.getBean(WeatherAggregatorOrdered.class).printStatus();

		System.out.println();
		System.out.println("\n--- Section 2b: aggregation ordered via the Ordered interface ---\n");
		ctx.getBean(StockAggregatorOrderedInterface.class).printStatus();

		System.out.println();
		System.out.println("\n--- Section 3: a @Bean method returning List<String> directly (not aggregated) ---\n");
		ctx.getBean(ExchangePreferences.class).printStatus();

		System.out.println();
		System.out.println("\n--- Section 4: @Order on individual @Bean methods (same bean class, two defs) ---\n");
		ctx.getBean(AlertDispatcher.class).printStatus();

		System.out.println();
		System.out.println("\n--- Section 5: aggregated beans in Map, and Set ---\n");
		ctx.getBean(AggregateWeatherStockMapSet.class).printStatus();

		ctx.close();
		System.out.println();

	}

}
