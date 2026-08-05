package com.example.annodemo.bpp;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;

@Component
public class AuditPostProcessor implements BeanPostProcessor {

	@Override
	public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
		if (beanName.equals("bppTargetBean")) {
			System.out.println("[bpp] AuditPostProcessor.postProcessBeforeInitialization runs" + " (on beanName: "
					+ beanName + ")");
		}
		return bean;
	}

	@Override
	public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
		if (beanName.equals("bppTargetBean")) {
			System.out.println("[bpp] AuditPostProcessor.postProcessAfterInitialization runs" + " (on beanName: "
					+ beanName + ")");
		}
		return bean;
	}

}
