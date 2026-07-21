package com.notifyChannels;

public class PushNotifier {

	private int retryCount;
	private int timeoutMillis;
	private String appId;

	public void setRetryCount(int retryCount) {
		this.retryCount = retryCount;
	}

	public void setTimeoutMillis(int timeoutMillis) {
		this.timeoutMillis = timeoutMillis;
	}

	public void setAppId(String appId) {
		this.appId = appId;
	}

	public void notify(String msg) {
		System.out.println("[Push to app " + appId + "] " + msg + " (retries=" + retryCount + ", timeout="
				+ timeoutMillis + "ms)");
	}

}
