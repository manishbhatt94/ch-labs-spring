package com.alertsDemo;

public class AlertService {

	private String smtpHost;

	private int smtpPort;

	private String fromAddress;

	private String subjectPrefix;

	private int priorityLevel;

	// setters for all 5 fields (needed for XML <property> injection)
	public void setSmtpHost(String smtpHost) {
		this.smtpHost = smtpHost;
	}

	public void setSmtpPort(int smtpPort) {
		this.smtpPort = smtpPort;
	}

	public void setFromAddress(String fromAddress) {
		this.fromAddress = fromAddress;
	}

	public void setSubjectPrefix(String subjectPrefix) {
		this.subjectPrefix = subjectPrefix;
	}

	public void setPriorityLevel(int priorityLevel) {
		this.priorityLevel = priorityLevel;
	}

	public void sendAlert(String message) {

		System.out.println("Connecting to " + smtpHost + ":" + smtpPort);
		System.out.println("From: " + fromAddress);
		System.out.println("Subject: " + subjectPrefix + " " + message);
		System.out.println("Priority: " + priorityLevel);
		System.out.println("-----");

	}

}