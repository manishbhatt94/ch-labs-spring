package com.crud;

import java.sql.PreparedStatement;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import com.entity.Employee;
import com.entity.EmployeeData;

public abstract class AbstractEmployeeCrud {

	protected JdbcTemplate jdbcTemplate;

	protected NamedParameterJdbcTemplate namedParameterJdbcTemplate;

	private static final RowMapper<Employee> employeeRowMapper = (rs, rowNum) -> {
		Employee mappedEmp = new Employee();
		mappedEmp.setId(rs.getInt("employee_id"));
		mappedEmp.setName(rs.getString("employee_name"));
		mappedEmp.setAddress(rs.getString("employee_address"));
		mappedEmp.setSalary(rs.getInt("employee_salary"));
		return mappedEmp;
	};

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
		int rowsAffected = jdbcTemplate.update(sql, name, address, salary);
		System.out.println("Rows affected: " + rowsAffected);
		System.out.println("Inserted employee: " + name + ".\n");

	}

	public void insertEmployee(Employee employee) {
		insertEmployee(employee.getName(), employee.getAddress(), employee.getSalary());
	}

	public void insertAndGetKey(Employee employee) {

		final String name = employee.getName();
		System.out.println("\nInserting employee: " + name + " into table spring_employee in database...");

		final String sql = "INSERT INTO spring_employee (employee_name, employee_address, employee_salary) VALUES (?, ?, ?)";

		PreparedStatementCreator psc = connection -> {
			PreparedStatement ps = connection.prepareStatement(sql, new String[] { "employee_id" });
			ps.setString(1, name);
			ps.setString(2, employee.getAddress());
			ps.setInt(3, employee.getSalary());
			return ps;
		};

		KeyHolder keyHolder = new GeneratedKeyHolder();

		int rowsAffected = jdbcTemplate.update(psc, keyHolder);

		// keyHolder.getKey() now contains the generated key
		System.out.println("Rows affected: " + rowsAffected);

		Number key = keyHolder.getKey();
		System.out.println("Inserted record's auto generated primary key = " + key);
		System.out.println("Inserted employee: " + name + ".\n");

	}

	public void updateEmployeeSalary(int employeeId, int newSalary) {

		System.out.println("\nUpdating salary of employee with ID: " + employeeId + " to " + newSalary
				+ " in table spring_employee in database...");
		String sql = "UPDATE spring_employee SET employee_salary = ? WHERE employee_id = ?";
		int rowsAffected = jdbcTemplate.update(sql, newSalary, employeeId);
		System.out.println("Rows affected: " + rowsAffected);
		System.out.println("Updated salary of employee with ID: " + employeeId + ".\n");

	}

	public void deleteEmployees(int fromId, int toId) {

		int rangeStart = fromId < toId ? fromId : toId;
		int rangeEnd = fromId < toId ? toId : fromId;
		System.out.println("\nDeleting employees with ID between " + rangeStart + " and " + rangeEnd + "...");

		String sql = "DELETE FROM spring_employee WHERE employee_id BETWEEN ? AND ?";
		int rowsAffected = jdbcTemplate.update(sql, rangeStart, rangeEnd);

		System.out.println("Rows affected: " + rowsAffected);
		System.out.println("Deleted employees.\n");

	}

	public void showEmployeeCount() {

		System.out.println("\nGetting count of employee records...");
		String sql = "SELECT COUNT(*) FROM spring_employee";

		int rowCount = jdbcTemplate.queryForObject(sql, Integer.class);

		System.out.println("Row count: " + rowCount + ".\n");

	}

	public void readBelowAverageSalaryEmployees() {

		System.out.println("\nGetting list of employees having salary below "
				+ "the average salary in the spring_employee table...");

		String sql = "SELECT * FROM spring_employee WHERE "
				+ "employee_salary < (SELECT AVG(se.employee_salary) FROM spring_employee se)";

		// Execute a query for a result list, given static SQL.
		// Uses a JDBC Statement, not a PreparedStatement
		List<Map<String, Object>> rows = jdbcTemplate.queryForList(sql);

		System.out.println("Found " + rows.size() + " employee(s) having salary below average.");
		System.out.println("Records fetched below:\n");

		for (Map<String, Object> row : rows) {
			System.out.print("    ->  ");
			for (Map.Entry<String, Object> data : row.entrySet()) {
				System.out.print(data.getKey() + "=" + data.getValue() + ";  ");
			}
			System.out.println();
		}

		System.out.println("\nDone reading all below average salary employees.\n");

	}

	/*
	 * SELECT *, TRIM(SUBSTRING_INDEX(employee_address, ',', -1)) AS
	 * `employee_state` FROM spring_employee;
	 */
	// https://docs.oracle.com/cd/E17952_01/mysql-8.0-en/string-functions.html#function_substring-index

	public void getStateWiseEmployeeCounts() {

		System.out.println("\nGetting state-wise counts of employees...");

		// @formatter:off
		String sql = "SELECT \n"
				+ "  TRIM(SUBSTRING_INDEX(employee_address, ',', -1)) AS employee_state, \n"
				+ "  COUNT(*) AS state_employee_count \n"
				+ "  FROM spring_employee GROUP BY employee_state \n"
				+ "  ORDER BY state_employee_count DESC;";
		// @formatter:on

		// Execute a query for a result list, given static SQL.
		// Uses a JDBC Statement, not a PreparedStatement
		List<Map<String, Object>> rows = jdbcTemplate.queryForList(sql);

		System.out.println("Retrieved " + rows.size() + " rows(s) -- i.e. this many states.");
		System.out.println("Records fetched below:\n");

		for (Map<String, Object> row : rows) {
			System.out.print("    ->  ");
			for (Map.Entry<String, Object> data : row.entrySet()) {
				System.out.print(data.getKey() + " = ");
				System.out.printf("%-20s", data.getValue().toString());
			}
			System.out.println();
		}

		System.out.println("\nDone reading state-wise counts of employees.\n");

	}

	public void readKarnatakaEmployees() {

		System.out.println("\nGetting list of employees residing in Karnataka state...");

		String sql = "SELECT * FROM spring_employee WHERE employee_address LIKE '%Karnataka'";

		// Execute a query given static SQL, mapping each row to a result object via a
		// RowMapper.
		// Uses a JDBC Statement, not a PreparedStatement.
		List<Employee> employees = jdbcTemplate.query(sql, employeeRowMapper);

		System.out.println("Found " + employees.size() + " employee(s) residing in Karnataka state.");
		System.out.println("Records fetched below:\n");

		for (Employee emp : employees) {
			System.out.println("    ->  " + emp);
		}

		System.out.println("\nDone reading all Karnataka state resident employees.\n");

	}

	public void readEmployeesHavingName(String namePart) {

		System.out.println("\nGetting of employees having '" + namePart + "' in their name...");

		String sql = "SELECT * FROM spring_employee WHERE employee_name LIKE ?";

		// Query given SQL to create a prepared statement from SQL and a list of
		// arguments to bind to the query, mapping each row to a result object via a
		// RowMapper.
		String pattern = "%" + namePart + "%";
		List<Employee> employees = jdbcTemplate.query(sql, employeeRowMapper, pattern);

		System.out.println("Found " + employees.size() + " employee(s) having '" + namePart + "' in their name.");
		System.out.println("Records fetched below:\n");

		for (Employee emp : employees) {
			System.out.println("    ->  " + emp);
		}

		System.out.println("\nDone reading all employees matching name substring.\n");

	}

	public void findEmployeeById(int employeeId) {

		System.out.println("\nFetching employee having employee_id = " + employeeId + "...");
		String sql = "SELECT * FROM spring_employee WHERE employee_id = ?";

		try {
			Employee emp = jdbcTemplate.queryForObject(sql, employeeRowMapper, employeeId);
			System.out.println("\nEmployee with employee_id = " + employeeId + " -- FOUND!");
			System.out.println("Found employee: " + emp + ".\n");
		} catch (EmptyResultDataAccessException ex) {
			System.out.println("\nEmployee with employee_id = " + employeeId + " -- NOT FOUND!\n");
		}

	}

	public void findEmployeesBySalaryAndState(int minSalary, String state) {

		System.out.println("\nFetching employees with salary >= " + minSalary + " residing in state containing '"
				+ state + "'...");

		// @formatter:off
	    String sql = "SELECT * FROM spring_employee \n"
	            + "  WHERE employee_salary >= :minSalary \n"
	            + "  AND employee_address LIKE :statePattern \n"
	            + "  ORDER BY employee_salary DESC";

		SqlParameterSource params = new MapSqlParameterSource()
				.addValue("minSalary", minSalary)
				.addValue("statePattern", "%" + state);
		// @formatter:on

		List<Employee> employees = namedParameterJdbcTemplate.query(sql, params, employeeRowMapper);

		System.out.println("Found " + employees.size() + " employee(s) matching criteria.");
		System.out.println("Records fetched below:\n");

		for (Employee emp : employees) {
			System.out.println("    ->  " + emp);
		}

		System.out.println("\nDone reading employees by salary and state.\n");

	}

	public void seedEmployeeTable() {

		List<Employee> employees = EmployeeData.getEmployees();
		System.out.println("\nSeeding table spring_employee with " + employees.size() + " employees...");

		String sql = "INSERT INTO spring_employee (employee_name, employee_address, employee_salary) VALUES (?, ?, ?)";
		int[][] batchUpdateCounts = jdbcTemplate.batchUpdate(sql, employees, 100, (ps, employee) -> {
			ps.setString(1, employee.getName());
			ps.setString(2, employee.getAddress());
			ps.setInt(3, employee.getSalary());
		});
		System.out.println("Batch Update Counts: " + Arrays.deepToString(batchUpdateCounts));
		System.out.println("Done seeding table spring_employee with " + employees.size() + " employees.\n");

	}

}
