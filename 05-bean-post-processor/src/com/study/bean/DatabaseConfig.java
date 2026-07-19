package com.study.bean;

public class DatabaseConfig {

	private String connectionUrl;

	private String username;

	// Standard getters and setters
	public String getConnectionUrl() {
		return connectionUrl;
	}

	public void setConnectionUrl(String connectionUrl) {
		this.connectionUrl = connectionUrl;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	// Custom initialization method defined in Spring XML config
	public void initConnectionPool() {

		System.out.println("   [Bean Init] Initialising pool using URL length: " + connectionUrl.length());

		// If the string was not trimmed *before* this step, an invalid URL exception
		// would happen
		if (connectionUrl.endsWith(" ")) {
			throw new IllegalStateException("CRITICAL ERROR: Connection URL contains trailing spaces!");
		}
		System.out.println("   [Bean Init] Connection pool successfully ready.");

	}

}
