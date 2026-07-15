package com.mainapp;

public class Employee {

	// Static block is executed when the class is loaded into memory, before any
	// object of the class is created.
	static {
		System.out.println("[Employee #static_block] Employee class is loaded");
	}

	private String employeeName;

	// Constructor is executed when an object of the class is created.
	public Employee() {
		System.out.println("[Employee #constructor] Employee object (Spring bean) is created");
	}

	public String objectDisplay() {
		return "Employee@" + Integer.toHexString(System.identityHashCode(this)) + "{employeeName=\"" + employeeName
				+ "\"}";
	}

	public void test() {
		System.out.println("[Employee #test] Employee bean " + objectDisplay() + " is working");
	}

	public String getEmployeeName() {
		return employeeName;
	}

	public void setEmployeeName(String employeeName) {
		this.employeeName = employeeName;
	}

	@Override
	public String toString() {
		return "Employee [employeeName=" + employeeName + "]";
	}

}
