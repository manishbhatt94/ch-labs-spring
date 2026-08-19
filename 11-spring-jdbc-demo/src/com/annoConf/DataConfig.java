package com.annoConf;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

@Configuration
@PropertySource("classpath:resources/database-config.properties")
public class DataConfig {

//	@Value("${jdbc.driverClassName}")
//	private String jdbcDriverClassName;
//
//	@Value("${jdbc.url}")
//	private String jdbcUrl;
//
//	@Value("${jdbc.username}")
//	private String jdbcUsername;
//
//	@Value("${jdbc.password}")
//	private String jdbcPassword;

	// Or, instead of injecting each property value like above using @Value and
	// defining individual fields, we can use Spring's
	// org.springframework.core.env.Environment interface which represents the
	// environment in which the current application is running. It models two
	// key aspects of the application environment: "profiles" & "properties".

	@Autowired
	private Environment env;

	@Bean
	public DataSource dataSource() {
		DriverManagerDataSource ds = new DriverManagerDataSource();

		// Approach 1: Call setters with values present in individual fields
		// each injected using @Value ...
//		ds.setDriverClassName(jdbcDriverClassName);
//		ds.setUrl(jdbcUrl);
//		ds.setUsername(jdbcUsername);
//		ds.setPassword(jdbcPassword);

		// Approach 2: Call setters with values present in Environment object
		// that Spring populates with all properties it loaded e.g. using
		// @PropertySource etc.
		ds.setDriverClassName(env.getProperty("jdbc.driverClassName"));
		ds.setUrl(env.getProperty("jdbc.url"));
		ds.setUsername(env.getProperty("jdbc.username"));
		ds.setPassword(env.getProperty("jdbc.password"));
		return ds;
	}

	@Bean
	public JdbcTemplate jdbcTemplate(DataSource dataSource) {
		return new JdbcTemplate(dataSource);
	}

}
