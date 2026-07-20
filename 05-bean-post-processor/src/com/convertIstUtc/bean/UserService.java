package com.convertIstUtc.bean;

import java.time.LocalDateTime;

import com.convertIstUtc.annotation.ConvertToUTC;

public class UserService {

	@ConvertToUTC
	private LocalDateTime registrationTime; // Logged in IST initially

	public LocalDateTime getRegistrationTime() {
		return registrationTime;
	}

	public void setRegistrationTime(LocalDateTime registrationTime) {
		this.registrationTime = registrationTime;
	}

	public void initUserService() {
		System.out.println("   [Init-Method] UserService verified registrationTime: " + registrationTime);
	}

	public void destroyUserService() {
		System.out.println("   [Destroy-Method] UserService verified registrationTime: " + registrationTime);
	}

}
