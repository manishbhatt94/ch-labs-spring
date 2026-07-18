package com.mainapp.withAnnotationsJSR250;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import com.datalayer.ConnectionFactory;

public class StudentReader {

	private Connection connection = null;

	@PostConstruct
	private void initDatabaseConnection() {

		System.out.println("\nStudentReader.init() method called");
		connection = ConnectionFactory.getConnection();
		System.out.println("Connection acquired: " + connection);

	}

	@PreDestroy
	private void destroyDatabaseConnection() {

		System.out.println("\nStudentReader.destroy() method called");
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

		String sql = "SELECT * FROM iiita_student ORDER BY student_id ASC LIMIT 10;";

		Statement statement = null;
		ResultSet rs = null;

		try {

			statement = connection.createStatement();
			rs = statement.executeQuery(sql);

			System.out.println("Records from `iiita_student` table:\n");

			while (rs.next()) {
				System.out.println("→ Student [");
				System.out.println("      • student_id = " + rs.getInt("student_id"));
				System.out.println("      • student_batch_year = " + rs.getInt("student_batch_year"));
				System.out.println("      • student_degree_programme = " + rs.getString("student_degree_programme"));
				System.out.println("      • student_enrollment_number = " + rs.getString("student_enrollment_number"));
				System.out.println("      • student_name = " + rs.getString("student_name"));
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
