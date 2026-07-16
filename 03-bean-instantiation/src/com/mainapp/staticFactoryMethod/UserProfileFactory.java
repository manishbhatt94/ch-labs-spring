package com.mainapp.staticFactoryMethod;

import java.time.LocalDateTime;
import java.util.Random;
import java.util.UUID;

public class UserProfileFactory {

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
