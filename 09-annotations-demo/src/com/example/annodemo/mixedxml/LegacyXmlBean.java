package com.example.annodemo.mixedxml;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

/**
 * @formatter:off
 * Deliberately NOT annotated with @Component (it is registered via a plain
 * <bean/> tag in XML instead - see beans-*.xml). It DOES carry JSR-250
 * @PostConstruct/@PreDestroy though, to prove the point of this demo:
 * those annotations only fire if <context:annotation-config/> (or
 * <context:component-scan/>) is present in the XML that defines this bean.
 * @formatter:on
 */
public class LegacyXmlBean {

	public LegacyXmlBean() {
		System.out.println("[mixedxml] LegacyXmlBean: constructor");
	}

	@PostConstruct
	public void init() {
		System.out.println("[mixedxml] LegacyXmlBean: @PostConstruct fired!");
	}

	@PreDestroy
	public void cleanup() {
		System.out.println("[mixedxml] LegacyXmlBean: @PreDestroy fired!");
	}

}
