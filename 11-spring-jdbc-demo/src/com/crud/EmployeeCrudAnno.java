package com.crud;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

@Component("employeeCrudBean")
public class EmployeeCrudAnno extends AbstractEmployeeCrud {

	@Autowired
	public void injectTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
		this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(jdbcTemplate);
	}

	@PostConstruct
	private void init() {
		dropEmployeeTable();
		createEmployeeTable();
		seedEmployeeTable();
	}

}
