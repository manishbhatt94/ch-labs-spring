package com.mainapp.withXmlAttributes;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.datalayer.ConnectionFactory;

public class CourseReader {

	private Connection connection = null;

	@SuppressWarnings("unused")
	private void initDatabaseConnection() {
		// Note: We can keep this method private if we want, since Spring can
		// still access it, even if private, using Reflection.

		System.out.println("\nCourseReader.init() method called");
		connection = ConnectionFactory.getConnection();
		System.out.println("Connection acquired: " + connection);

	}

	@SuppressWarnings("unused")
	private void destroyDatabaseConnection() {
		// Note: We can keep this method private if we want, since Spring can
		// still access it, even if private, using Reflection.

		System.out.println("\nCourseReader.destroy() method called");
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

		String sql = "SELECT * FROM iiita_course ORDER BY course_id ASC LIMIT 10;";

		Statement statement = null;
		ResultSet rs = null;

		try {

			statement = connection.createStatement();
			rs = statement.executeQuery(sql);

			System.out.println("Records from `iiita_course` table:\n");

			while (rs.next()) {
				System.out.println("→ Course [");
				System.out.println("      • course_id = " + rs.getInt("course_id"));
				System.out.println("      • course_name = " + rs.getString("course_name"));
				System.out.println("      • course_instructor = " + rs.getString("course_instructor"));
				System.out.println("      • course_credits = " + rs.getInt("course_credits"));
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
