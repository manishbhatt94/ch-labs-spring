package com.example.annodemo.bpp.ordered;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;

@Component
public class BetaPostProcessor implements BeanPostProcessor, Ordered {

	@Override
	public int getOrder() {
		return 200;
	}

	@Override
	public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
		if (beanName.equals("bppTargetBean")) {
			System.out.println("[bpp] (Ordered:200) BetaPostProcessor.postProcessBeforeInitialization runs"
					+ " (on beanName: " + beanName + ")");
		}
		return bean;
	}

	@Override
	public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
		if (beanName.equals("bppTargetBean")) {
			System.out.println("[bpp] (Ordered:200) BetaPostProcessor.postProcessAfterInitialization runs"
					+ " (on beanName: " + beanName + ")");
		}
		return bean;
	}

}
