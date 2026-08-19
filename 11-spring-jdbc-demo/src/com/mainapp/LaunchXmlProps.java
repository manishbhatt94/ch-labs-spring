package com.mainapp;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.crud.EmployeeCrudXml;

public class LaunchXmlProps {

	public static void main(String[] args) {

		ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext(
				"resources/beans-properties-config.xml");

		EmployeeCrudXml crud = ctx.getBean("employeeCrud", EmployeeCrudXml.class);

		crud.insertEmployee("Method Man", "Park Hill Project, Staten Island, New York State", 23000);
		crud.insertEmployee("Ol' Dirty Bastard", "Park Hill Project, Staten Island, New York State", 31000);

		crud.updateEmployeeSalary(1, 11111);
		crud.updateEmployeeSalary(2, 22222);
		crud.updateEmployeeSalary(3, 33333);

		crud.deleteEmployees(11, 15);

		crud.showEmployeeCount();

		crud.readBelowAverageSalaryEmployees();
		crud.readKarnatakaEmployees();
		crud.readEmployeesHavingName("Kulkarni");

		crud.findEmployeeById(3017);
		crud.findEmployeeById(19);

		ctx.close();

	}

}
