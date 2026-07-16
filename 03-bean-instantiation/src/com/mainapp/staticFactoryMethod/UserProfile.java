package com.mainapp.staticFactoryMethod;

import java.time.LocalDateTime;
import java.util.Random;
import java.util.UUID;

public class UserProfile {

	private String userId;

	private String userName;

	private int luckyNumber;

	private LocalDateTime createdAt;

	public UserProfile() {
		super();
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public int getLuckyNumber() {
		return luckyNumber;
	}

	public void setLuckyNumber(int luckyNumber) {
		this.luckyNumber = luckyNumber;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}

	@Override
	public String toString() {
		return "UserProfile [userId=" + userId + ", userName=" + userName + ", luckyNumber=" + luckyNumber
				+ ", createdAt=" + createdAt + "]";
	}

	public static UserProfile create() {
		UserProfile userProfile = new UserProfile();

		userProfile.setUserName(null);

		userProfile.setUserId(UUID.randomUUID().toString());
		userProfile.setLuckyNumber((new Random()).nextInt(99_000_000));
		userProfile.setCreatedAt(LocalDateTime.now());

		return userProfile;
	}

	public static UserProfile of(String userName) {
		UserProfile userProfile = new UserProfile();

		userProfile.setUserName(userName);

		userProfile.setUserId(UUID.randomUUID().toString());
		userProfile.setLuckyNumber((new Random()).nextInt(99_000_000));
		userProfile.setCreatedAt(LocalDateTime.now());

		return userProfile;
	}

}
