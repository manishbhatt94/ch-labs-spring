package com.notifyChannels;

public class EmailNotifier {

	private int retryCount;
	private int timeoutMillis;
	private String smtpHost;

	public void setRetryCount(int retryCount) {
		this.retryCount = retryCount;
	}

	public void setTimeoutMillis(int timeoutMillis) {
		this.timeoutMillis = timeoutMillis;
	}

	public void setSmtpHost(String smtpHost) {
		this.smtpHost = smtpHost;
	}

	public void notify(String msg) {
		System.out.println("[Email via " + smtpHost + "] " + msg + " (retries=" + retryCount + ", timeout="
				+ timeoutMillis + "ms)");
	}

}
