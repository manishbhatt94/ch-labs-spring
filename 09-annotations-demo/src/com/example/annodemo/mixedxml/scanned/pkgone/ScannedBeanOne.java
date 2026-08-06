package com.example.annodemo.mixedxml.scanned.pkgone;

import org.springframework.stereotype.Component;

/**
 * Lives in package #1 of the two base-packages given to
 * <context:component-scan/> in beans-with-component-scan.xml. Never declared as
 * a <bean/> anywhere - discovery is entirely the scan's doing.
 */
@Component
public class ScannedBeanOne {

	public ScannedBeanOne() {
		System.out.println("[component-scan] ScannedBeanOne constructed (found via component-scan, package #1)");
	}

}
