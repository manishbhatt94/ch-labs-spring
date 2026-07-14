package com.mainapp;

public class Salary {

	static {
		System.out.println("[Salary #static_block] Salary class is loaded");
	}

	public Salary() {
		System.out.println("[Salary #constructor] Salary object (Spring bean) is created");
	}

	public void test() {
		System.out.println("[Salary #test] Salary bean is working");
	}

}
