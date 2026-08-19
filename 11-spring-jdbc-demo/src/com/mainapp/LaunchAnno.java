package com.mainapp;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.annoConf.AppConfig;
import com.annoConf.DataConfig;
import com.crud.EmployeeCrudAnno;

public class LaunchAnno {

	public static void main(String[] args) {

		AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(DataConfig.class,
				AppConfig.class);

		EmployeeCrudAnno crud = ctx.getBean("employeeCrudBean", EmployeeCrudAnno.class);

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
