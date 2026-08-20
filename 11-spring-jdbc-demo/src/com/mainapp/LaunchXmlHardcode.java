package com.mainapp;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.crud.EmployeeCrudXml;
import com.entity.Employee;

public class LaunchXmlHardcode {

	public static void main(String[] args) {

		ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("resources/beans-hardcode-config.xml");

		EmployeeCrudXml crud = ctx.getBean("employeeCrud", EmployeeCrudXml.class);

		long startTime = System.nanoTime();

		crud.insertEmployee("Method Man", "Park Hill Project, Staten Island, New York", 23000);
		crud.insertEmployee("Ol' Dirty Bastard", "Park Hill Project, Staten Island, New York", 31000);

		crud.updateEmployeeSalary(1, 11111);
		crud.updateEmployeeSalary(2, 22222);
		crud.updateEmployeeSalary(3, 33333);

		crud.deleteEmployees(11, 15);

		crud.readBelowAverageSalaryEmployees();
		crud.readKarnatakaEmployees();
		crud.readEmployeesHavingName("Kulkarni");

		crud.findEmployeeById(3017);
		crud.findEmployeeById(19);

		crud.insertAndGetKey(new Employee("Inspectah Deck", "The Bronx, New York", 26000));

		crud.showEmployeeCount();

		crud.getStateWiseEmployeeCounts();

		crud.findEmployeesBySalaryAndState(55000, "Karnataka");
		crud.findEmployeesBySalaryAndState(50000, "Uttar Pradesh");

		long endTime = System.nanoTime();
		long elapsedMillis = (endTime - startTime) / 1_000_000;

		System.out.println();
		System.out.println("===============================================");
		System.out.println("Time elapsed for CRUD operations: " + elapsedMillis + " ms");
		System.out.println("===============================================\n");

		ctx.close();

	}

}
