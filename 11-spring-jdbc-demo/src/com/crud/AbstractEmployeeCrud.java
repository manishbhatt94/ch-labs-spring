package com.crud;

import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;

import com.entity.Employee;
import com.entity.EmployeeData;

public abstract class AbstractEmployeeCrud {

	protected JdbcTemplate jdbcTemplate;

	public void createEmployeeTable() {

		System.out.println("\nCreating table spring_employee in database...");
		// @formatter:off
		String sql = "CREATE TABLE IF NOT EXISTS `spring_employee` (\n"
				+ "  `employee_id` int NOT NULL AUTO_INCREMENT,\n"
				+ "  `employee_name` varchar(50) NOT NULL,\n"
				+ "  `employee_address` varchar(150) DEFAULT NULL,\n"
				+ "  `employee_salary` int NOT NULL,\n"
				+ "  PRIMARY KEY (`employee_id`)\n"
				+ ")";
		// @formatter:on

		jdbcTemplate.execute(sql);
		System.out.println("Table spring_employee created successfully in database.\n");

	}

	public void dropEmployeeTable() {

		System.out.println("\nDropping table spring_employee in database...");
		String sql = "DROP TABLE IF EXISTS `spring_employee`";

		jdbcTemplate.execute(sql);

		System.out.println("Table spring_employee dropped successfully in database.\n");

	}

	public void insertEmployee(String name, String address, int salary) {

		System.out.println("\nInserting employee: " + name + " into table spring_employee in database...");
		String sql = "INSERT INTO spring_employee (employee_name, employee_address, employee_salary) VALUES (?, ?, ?)";
		jdbcTemplate.update(sql, name, address, salary);
		System.out.println("Inserted employee: " + name + ".\n");

	}

	public void insertEmployee(Employee employee) {

		insertEmployee(employee.getName(), employee.getAddress(), employee.getSalary());

	}

	public void seedEmployeeTable() {

		List<Employee> employees = EmployeeData.getEmployees();
		System.out.println("\nSeeding table spring_employee with " + employees.size() + " employees...");

		String sql = "INSERT INTO spring_employee (employee_name, employee_address, employee_salary) VALUES (?, ?, ?)";
		jdbcTemplate.batchUpdate(sql, employees, 100, (ps, employee) -> {
			ps.setString(1, employee.getName());
			ps.setString(2, employee.getAddress());
			ps.setInt(3, employee.getSalary());
		});
		System.out.println("Done seeding table spring_employee with " + employees.size() + " employees.\n");

	}

}
