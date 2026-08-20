package com.crud;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

public class EmployeeCrudXml extends AbstractEmployeeCrud {

	public EmployeeCrudXml(JdbcTemplate jdbcTemplate) {
		super();
		this.jdbcTemplate = jdbcTemplate;
		this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(jdbcTemplate);
	}

	@SuppressWarnings("unused")
	private void init() {
		dropEmployeeTable();
		createEmployeeTable();
		seedEmployeeTable();
	}

}
