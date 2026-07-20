package com.convertIstUtc.bean;

import java.time.LocalDateTime;

import com.convertIstUtc.annotation.ConvertToUTC;

public class DeliveryService {

	@ConvertToUTC
	private LocalDateTime dispatchTime; // Logged in IST initially

	public LocalDateTime getDispatchTime() {
		return dispatchTime;
	}

	public void setDispatchTime(LocalDateTime dispatchTime) {
		this.dispatchTime = dispatchTime;
	}

	public void initDeliveryService() {
		System.out.println("   [Init-Method] DeliveryService verified dispatchTime: " + dispatchTime);
	}

	public void destroyDeliveryService() {
		System.out.println("   [Destroy-Method] DeliveryService verified dispatchTime: " + dispatchTime);
	}

}
