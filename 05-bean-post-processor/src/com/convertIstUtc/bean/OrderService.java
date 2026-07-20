package com.convertIstUtc.bean;

import java.time.LocalDateTime;

import com.convertIstUtc.annotation.ConvertToUTC;

public class OrderService {

	@ConvertToUTC
	private LocalDateTime bookingTime; // Logged in IST initially

	public LocalDateTime getBookingTime() {
		return bookingTime;
	}

	public void setBookingTime(LocalDateTime bookingTime) {
		this.bookingTime = bookingTime;
	}

	public void initOrderService() {
		System.out.println("   [Init-Method] OrderService verified bookingTime: " + bookingTime);
	}

	public void destroyOrderService() {
		System.out.println("   [Destroy-Method] OrderService verified bookingTime: " + bookingTime);
	}

}
