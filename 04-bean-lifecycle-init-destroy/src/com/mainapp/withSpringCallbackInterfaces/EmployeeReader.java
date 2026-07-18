package com.mainapp.withSpringCallbackInterfaces;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;

import com.datalayer.ConnectionFactory;

public class EmployeeReader implements InitializingBean, DisposableBean {

	private Connection connection = null;

	@Override
	public void afterPropertiesSet() throws Exception {

		System.out.println("\nEmployeeReader.init() method called");
		connection = ConnectionFactory.getConnection();
		System.out.println("Connection acquired: " + connection);

	}

	@Override
	public void destroy() throws Exception {

		System.out.println("\nEmployeeReader.destroy() method called");
		ConnectionFactory.closeConnection(connection);
		connection = null;
		System.out.println("Connection released.");

	}

	public void readRecords() {

		System.out.println("\n");
		if (connection == null) {
			System.out.println("Connection is null. Cannot proceed with database access method. Exiting now!");
			return;
		}

		String sql = "SELECT * FROM hbn_employee ORDER BY employee_id ASC LIMIT 10;";

		Statement statement = null;
		ResultSet rs = null;

		try {

			statement = connection.createStatement();
			rs = statement.executeQuery(sql);

			System.out.println("Records from `hbn_employee` table:\n");

			while (rs.next()) {
				System.out.println("→ Employee [");
				System.out.println("      • employee_id = " + rs.getInt("employee_id"));
				System.out.println("      • employee_name = " + rs.getString("employee_name"));
				System.out.println("      • employee_salary = " + rs.getInt("employee_salary"));
				System.out.println("      • employee_address = " + rs.getString("employee_address"));
				System.out.println("  ]");
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				rs.close();
				statement.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		System.out.println();

	}

}
