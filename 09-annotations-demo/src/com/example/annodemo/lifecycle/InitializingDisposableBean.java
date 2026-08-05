package com.example.annodemo.lifecycle;

import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

/** Callback-interface style - unchanged between XML and annotation config. */
@Component
public class InitializingDisposableBean implements InitializingBean, DisposableBean {

	public InitializingDisposableBean() {
		System.out.println("[lifecycle] InitializingDisposableBean: constructor");
	}

	@Override
	public void afterPropertiesSet() {
		System.out.println("[lifecycle] InitializingDisposableBean: afterPropertiesSet()");
	}

	@Override
	public void destroy() {
		System.out.println("[lifecycle] InitializingDisposableBean: destroy()");
	}

}
