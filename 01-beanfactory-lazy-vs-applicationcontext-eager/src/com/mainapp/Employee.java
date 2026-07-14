package com.mainapp;

import java.util.Scanner;

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

	public String getPermission() {

		Scanner scanner = new Scanner(System.in);
		System.out.print("Enter your role (admin/user): ");

		String role = scanner.nextLine().trim().toLowerCase();
		scanner.close();

		if (role.equals("admin")) {
			return "access_granted";
		} else {
			return "access_denied";
		}

	}

}
