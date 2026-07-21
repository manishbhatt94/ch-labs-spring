package com.notifyChannels;

public class SmsNotifier {

	private int retryCount;
	private int timeoutMillis;
	private String gatewayNumber;

	public void setRetryCount(int retryCount) {
		this.retryCount = retryCount;
	}

	public void setTimeoutMillis(int timeoutMillis) {
		this.timeoutMillis = timeoutMillis;
	}

	public void setGatewayNumber(String gatewayNumber) {
		this.gatewayNumber = gatewayNumber;
	}

	public void notify(String msg) {
		System.out.println("[SMS via " + gatewayNumber + "] " + msg + " (retries=" + retryCount + ", timeout="
				+ timeoutMillis + "ms)");
	}

}
