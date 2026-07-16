package com.mainapp.usingConstructor;

public class Car {

	private String brand;

	private String model;

	private int releaseYear;

	private double price;

	private Engine engine;

	public Car() {
		super();
	}

	public Car(String brand, String model, int releaseYear, double price, Engine engine) {
		super();
		this.brand = brand;
		this.model = model;
		this.releaseYear = releaseYear;
		this.price = price;
		this.engine = engine;
	}

	public String getBrand() {
		return brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public int getReleaseYear() {
		return releaseYear;
	}

	public void setReleaseYear(int releaseYear) {
		this.releaseYear = releaseYear;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public Engine getEngine() {
		return engine;
	}

	public void setEngine(Engine engine) {
		this.engine = engine;
	}

	@Override
	public String toString() {
		return "Car [brand=" + brand + ", model=" + model + ", releaseYear=" + releaseYear + ", price=" + price
				+ ", engine=" + engine + "]";
	}

}
