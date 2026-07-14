package com.mainapp;

public class Project {

	static {
		System.out.println("[Project #static_block] Project class is loaded");
	}

	public Project() {
		System.out.println("[Project #constructor] Project object (Spring bean) is created");
	}

	public void test() {
		System.out.println("[Project #test] Project bean is working");
	}

}
