package com.example.annodemo.bpp;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;

/**
 * XML equivalent: <bean class="...OrderPostProcessor"/> (Spring auto-detects
 * any registered bean implementing BeanPostProcessor and applies it to every
 * OTHER bean's initialization - same auto-detection rule as in XML-based
 * ApplicationContext.)
 *
 * Two BPPs are registered here (this one + AuditPostProcessor) to show that ALL
 * registered BPPs run their "before" method (in registration order), then the
 * target bean's init-method runs, then ALL BPPs run their "after" method.
 */
@Component
public class OrderPostProcessor implements BeanPostProcessor {

	@Override
	public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
		if (beanName.equals("bppTargetBean")) {
			System.out.println("[bpp] OrderPostProcessor.postProcessBeforeInitialization runs" + " (on beanName: "
					+ beanName + ")");
		}
		return bean;
	}

	@Override
	public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
		if (beanName.equals("bppTargetBean")) {
			System.out.println("[bpp] OrderPostProcessor.postProcessAfterInitialization runs" + " (on beanName: "
					+ beanName + ")");
		}
		return bean;
	}
}
