package com.propsInjection;

import java.util.Properties;

public class MailSender {

	private Properties mailServerSettings; // -- SMTP config, populated via <props>

	private Properties featureFlags; // -- populated via the shortcut multi-line <value> form

	public void setMailServerSettings(Properties mailServerSettings) {
		this.mailServerSettings = mailServerSettings;
	}

	public void setFeatureFlags(Properties featureFlags) {
		this.featureFlags = featureFlags;
	}

	public Properties getMailServerSettings() {
		return mailServerSettings;
	}

	public Properties getFeatureFlags() {
		return featureFlags;
	}

	@Override
	public String toString() {
		return "MailSender [mailServerSettings=" + mailServerSettings + ", featureFlags=" + featureFlags + "]";
	}

}
