package com.crud;

import org.springframework.jdbc.core.JdbcTemplate;

public class EmployeeCrudXml extends AbstractEmployeeCrud {

	public EmployeeCrudXml(JdbcTemplate jdbcTemplate) {
		super();
		this.jdbcTemplate = jdbcTemplate;
	}

	@SuppressWarnings("unused")
	private void init() {
		dropEmployeeTable();
		createEmployeeTable();
		seedEmployeeTable();
	}

}
