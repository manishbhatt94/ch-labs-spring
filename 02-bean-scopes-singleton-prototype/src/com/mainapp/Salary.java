package com.mainapp;

public class Salary {

	// Static block is executed when the class is loaded into memory, before any
	// object of the class is created.
	static {
		System.out.println("[Salary #static_block] Salary class is loaded");
	}

	private double basicPay;

	// Constructor is executed when an object of the class is created.
	public Salary() {
		System.out.println("[Salary #constructor] Salary object (Spring bean) is created");
	}

	public String objectDisplay() {
		return "Salary@" + Integer.toHexString(System.identityHashCode(this)) + "{basicPay=" + basicPay + "}";
	}

	public void test() {
		System.out.println("[Salary #test] Salary bean " + objectDisplay() + " is working");
	}

	public double getBasicPay() {
		return basicPay;
	}

	public void setBasicPay(double basicPay) {
		this.basicPay = basicPay;
	}

	@Override
	public String toString() {
		return "Salary [basicPay=" + basicPay + "]";
	}

}
