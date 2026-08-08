package di.beans.collections.alerts;

/**
 * Plain POJO -- deliberately NOT @Component; only ever created via the two
 *
 * @Bean factory methods in AlertConfig, so we can demonstrate per-@Bean-method
 * @Order on multiple bean definitions of the SAME class.
 */
public class AlertChannel {

	private final String name;

	public AlertChannel(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

}
