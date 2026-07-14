package com.mainapp;

public class Employee {

	static {
		System.out.println("[Employee #static_block] Employee class is loaded");
	}

	public Employee() {
		System.out.println("[Employee #constructor] Employee object (Spring bean) is created");
	}

	public void test() {
		System.out.println("[Employee #test] Employee bean is working");
	}

}
