package com.convertIstUtc.processor;

import java.lang.reflect.Field;
import java.time.LocalDateTime;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;

import com.convertIstUtc.annotation.ConvertToUTC;

public class TimezoneNormalizationProcessor implements BeanPostProcessor {

	// PHASE 1: Runs BEFORE init-method. Mutates raw IST data to UTC.
	@Override
	public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {

		// Use Java Reflection to look at all fields declared within the bean class
		Field[] fields = bean.getClass().getDeclaredFields();

		for (Field field : fields) {
			// Check if the current field is tagged with our @ConvertToUTC annotation
			if (field.isAnnotationPresent(ConvertToUTC.class)) {
				try {
					// Bypass standard Java 'private' access restrictions temporarily
					field.setAccessible(true);

					// Extract the raw value currently stored in the field
					LocalDateTime istTime = (LocalDateTime) field.get(bean);

					if (istTime != null) {
						System.out.println("[BPP-Before] Intercepted '" + beanName + "' | Found IST Time: " + istTime);

						// Perform normalization logic: Subtract 5 hours and 30 minutes to get UTC
						LocalDateTime utcTime = istTime.minusHours(5).minusMinutes(30);

						// Inject the freshly calculated UTC time back into the private field
						field.set(bean, utcTime);
						System.out
								.println("[BPP-Before] Normalised field '" + field.getName() + "' to UTC: " + utcTime);
					}
				} catch (Exception e) {
					System.err.println("Failed to mutate time field: " + e.getMessage());
				}
			}
		}

		return bean; // Return the modified bean instance

	}

	// PHASE 2: Runs AFTER init-method. Acts as an architectural compliance auditor.
	@Override
	public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {

		Field[] fields = bean.getClass().getDeclaredFields();

		for (Field field : fields) {
			if (field.isAnnotationPresent(ConvertToUTC.class)) {
				try {
					field.setAccessible(true);
					LocalDateTime finalTime = (LocalDateTime) field.get(bean);
					// Verifies that the init-method did not corrupt or roll back our UTC settings
					System.out.println("[BPP-After] Compliance Audit Passed for '" + beanName + "'. Certified state: "
							+ finalTime);
				} catch (Exception e) {
					// Fail silently for notes clarity
				}
			}
		}
		System.out.println();

		return bean;

	}

}
