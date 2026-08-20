package com.annoConf;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.core.JdbcTemplate;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

@Configuration
@PropertySource("classpath:resources/database-config.properties")
public class PooledDataConfig {

	@Autowired
	private Environment env;

	@Bean
	public DataSource dataSource() {

		HikariConfig hikariConfig = new HikariConfig();

		// DB Configuration like JDBC URL, DB Username, DB Password
		// -- reusing the same properties file as plain DataConfig
		hikariConfig.setJdbcUrl(env.getProperty("jdbc.url"));
		hikariConfig.setUsername(env.getProperty("jdbc.username"));
		hikariConfig.setPassword(env.getProperty("jdbc.password"));

		// HikariCP with a modern driver (mysql-connector-8.2.0, JDBC 4.0+) auto-detects
		// the driver class from the JDBC URL via DriverManager's SPI mechanism, so it's
		// optional (Claude):
		hikariConfig.setDriverClassName(env.getProperty("jdbc.driverClassName"));

		// Connection Pool related options
		hikariConfig.setMinimumIdle(5);
		hikariConfig.setMaximumPoolSize(10);
		hikariConfig.setPoolName("SpringHikariPool");

		return new HikariDataSource(hikariConfig); // Pool ready.
	}

	@Bean
	public JdbcTemplate jdbcTemplate(DataSource dataSource) {
		return new JdbcTemplate(dataSource);
	}

}