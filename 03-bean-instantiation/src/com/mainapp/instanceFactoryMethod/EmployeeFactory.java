package com.mainapp.instanceFactoryMethod;

import java.time.LocalDate;
import java.util.Random;

public class EmployeeFactory {

	private Organization organization;

	private static final String[] DEPARTMENTS_LIST = { "HR", "Finance", "IT", "Sales", "Marketing" };

	public EmployeeFactory(Organization organization) {
		if (organization == null) {
			throw new IllegalArgumentException("Organization cannot be null");
		}
		this.organization = organization;
	}

	// Instance Factory method 1:
	public Employee createEmployee(String employeeName) {

		Employee employee = constructEmployee();
		employee.setEmployeeName(employeeName);
		employee.setBankAccount(null);

		return employee;
	}

	// Instance Factory method 2:
	public Employee createEmployee(String employeeName, BankAccount employeeBankAccount) {

		Employee employee = constructEmployee();
		employee.setEmployeeName(employeeName);
		employee.setBankAccount(employeeBankAccount);

		return employee;
	}

	private Employee constructEmployee() {
		Employee employee = new Employee();
		employee.setEmployeeId("EMP-" + organization.getOrgId() + "-" + System.currentTimeMillis());
		employee.setJoiningDate(LocalDate.now());
		employee.setOrganization(organization);
		String department = DEPARTMENTS_LIST[(new Random()).nextInt(DEPARTMENTS_LIST.length)];
		employee.setDepartment(department);

		return employee;
	}
}
