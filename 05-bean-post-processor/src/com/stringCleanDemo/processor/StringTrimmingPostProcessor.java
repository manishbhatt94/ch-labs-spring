package com.stringCleanDemo.processor;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;

import com.stringCleanDemo.bean.DatabaseConfig;

public class StringTrimmingPostProcessor implements BeanPostProcessor {

	// PHASE 1: Runs BEFORE init-method. Perfect for data sanitization.
	@Override
	public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {

		if (bean instanceof DatabaseConfig) {
			DatabaseConfig config = (DatabaseConfig) bean;
			System.out.println("[BPP - Before Init] Checking raw URL string: '" + config.getConnectionUrl() + "'");

			// Clean up the whitespace bugs right before the bean runs its internal init
			// logic
			if (config.getConnectionUrl() != null) {
				config.setConnectionUrl(config.getConnectionUrl().trim());
			}
			if (config.getUsername() != null) {
				config.setUsername(config.getUsername().trim());
			}

			System.out.println("[BPP - Before Init] Sanitized URL to: '" + config.getConnectionUrl() + "'");
		}
		return bean; // Must return the bean instance (modified or unmodified)

	}

	// PHASE 2: Runs AFTER init-method. Perfect for final verification, logging, or
	// proxying.
	@Override
	public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {

		if (bean instanceof DatabaseConfig) {
			DatabaseConfig config = (DatabaseConfig) bean;
			System.out.println("[BPP - After Init] Post-validation check for bean: " + beanName);
			System.out.println("[BPP - After Init] Final clean username configuration: '" + config.getUsername() + "'");
		}
		System.out.println();

		return bean; // Must return the bean instance

	}

}
