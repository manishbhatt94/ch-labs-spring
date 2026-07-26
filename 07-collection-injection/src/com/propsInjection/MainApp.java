package com.propsInjection;

import java.util.Properties;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class MainApp {

	public static void main(String[] args) {

		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("com/propsInjection/beans.xml");

		System.out.println("######### Properties Injection (XML) Demo ################\n\n");

		Properties appDefaults = context.getBean("appDefaults", Properties.class);
		System.out.println("appDefaults.getClass() => " + appDefaults.getClass());
		System.out.println("appDefaults: " + appDefaults + "   (Hashtable-based -> iteration order NOT guaranteed,"
				+ " unlike the default List/Set/Map implementations)");
		System.out.println("appDefaults.getProperty(\"app.version\") => " + appDefaults.getProperty("app.version"));
		System.out.println("appDefaults.getProperty(\"app.region\", \"ap-south-1\") => "
				+ appDefaults.getProperty("app.region", "ap-south-1")
				+ "   (key doesn't exist -> falls back to the supplied default)");

		System.out.println();

		Properties appDefaultsDevelopment = context.getBean("appDefaultsDevelopment", Properties.class);
		System.out.println("appDefaultsDevelopment: " + appDefaultsDevelopment);

		System.out.println();

		Properties appDefaultsDevOverriden = context.getBean("appDefaultsDevOverriden", Properties.class);
		System.out.println("appDefaultsDevOverriden: " + appDefaultsDevOverriden);

		System.out.println();

		Properties databaseConfig = context.getBean("databaseConfig", Properties.class);
		System.out.println("databaseConfig: " + databaseConfig);

		System.out.println();

		MailSender mailSender = context.getBean("mailSender", MailSender.class);
		System.out.println(mailSender);

		System.out.println();

		Properties mailSettings = mailSender.getMailServerSettings();
		System.out
				.println("mailSettings.getProperty(\"mail.smtp.port\") => " + mailSettings.getProperty("mail.smtp.port")
						+ "   (2 <prop key=\"mail.smtp.port\"> entries declared in XML -> last one, 2525, wins)");

		System.out.println();

		Properties flags = mailSender.getFeatureFlags();
		System.out.println("featureFlags (parsed from a plain <value> block, Properties.load()-style): " + flags);
		System.out.println("featureFlags.getProperty(\"darkMode\") => " + flags.getProperty("darkMode"));

		context.close();

		System.out.println();

	}

}
