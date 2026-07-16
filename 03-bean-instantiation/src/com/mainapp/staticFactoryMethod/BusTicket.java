package com.mainapp.staticFactoryMethod;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class BusTicket {

	private String sourceCity;

	private String destinationCity;

	private double fare;

	private LocalDate journeyDate;

	private final LocalDateTime bookingTime;

	public BusTicket(String sourceCity, String destinationCity, double fare, LocalDate journeyDate,
			LocalDateTime bookingTime) {
		super();
		this.sourceCity = sourceCity;
		this.destinationCity = destinationCity;
		this.fare = fare;
		this.journeyDate = journeyDate;
		this.bookingTime = bookingTime;
	}

	public String getSourceCity() {
		return sourceCity;
	}

	public void setSourceCity(String sourceCity) {
		this.sourceCity = sourceCity;
	}

	public String getDestinationCity() {
		return destinationCity;
	}

	public void setDestinationCity(String destinationCity) {
		this.destinationCity = destinationCity;
	}

	public double getFare() {
		return fare;
	}

	public void setFare(double fare) {
		this.fare = fare;
	}

	public LocalDate getJourneyDate() {
		return journeyDate;
	}

	public void setJourneyDate(LocalDate journeyDate) {
		this.journeyDate = journeyDate;
	}

	public LocalDateTime getBookingTime() {
		return bookingTime;
	}

	@Override
	public String toString() {
		return "BusTicket [sourceCity=" + sourceCity + ", destinationCity=" + destinationCity + ", fare=" + fare
				+ ", journeyDate=" + journeyDate + ", bookingTime=" + bookingTime + "]";
	}

}
