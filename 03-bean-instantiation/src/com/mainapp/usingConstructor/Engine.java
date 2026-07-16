package com.mainapp.usingConstructor;

public class Engine {

	private int numCylinders;

	private double totalEngineVolume;

	private double peakHorsePower;

	private double peakTorque;

	public Engine() {
		super();
	}

	public Engine(int numCylinders, double totalEngineVolume, double peakHorsePower, double peakTorque) {
		super();
		this.numCylinders = numCylinders;
		this.totalEngineVolume = totalEngineVolume;
		this.peakHorsePower = peakHorsePower;
		this.peakTorque = peakTorque;
	}

	public int getNumCylinders() {
		return numCylinders;
	}

	public void setNumCylinders(int numCylinders) {
		this.numCylinders = numCylinders;
	}

	public double getTotalEngineVolume() {
		return totalEngineVolume;
	}

	public void setTotalEngineVolume(double totalEngineVolume) {
		this.totalEngineVolume = totalEngineVolume;
	}

	public double getPeakHorsePower() {
		return peakHorsePower;
	}

	public void setPeakHorsePower(double peakHorsePower) {
		this.peakHorsePower = peakHorsePower;
	}

	public double getPeakTorque() {
		return peakTorque;
	}

	public void setPeakTorque(double peakTorque) {
		this.peakTorque = peakTorque;
	}

	@Override
	public String toString() {
		return "Engine [numCylinders=" + numCylinders + ", totalEngineVolume=" + totalEngineVolume + ", peakHorsePower="
				+ peakHorsePower + ", peakTorque=" + peakTorque + "]";
	}

}
