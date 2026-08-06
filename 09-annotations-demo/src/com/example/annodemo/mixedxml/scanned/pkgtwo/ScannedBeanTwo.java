package com.example.annodemo.mixedxml.scanned.pkgtwo;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.springframework.stereotype.Component;

/**
 * Lives in package #2 of the two base-packages given to component-scan. Also
 * carries @PostConstruct/@PreDestroy - on purpose, to prove that
 * <context:component-scan/> ALONE (no separate <context:annotation-config/>
 * needed alongside it) is enough to activate JSR-250 annotation processing,
 * unlike the plain <bean/> declarations in the other two XMLs in this demo.
 */
@Component
public class ScannedBeanTwo {

	public ScannedBeanTwo() {
		System.out.println("[component-scan] ScannedBeanTwo constructed (found via component-scan, package #2)");
	}

	@PostConstruct
	public void init() {
		System.out.println("[component-scan] ScannedBeanTwo: @PostConstruct fired!"
				+ " (component-scan implied annotation-config, no separate tag needed)");
	}

	@PreDestroy
	public void cleanup() {
		System.out.println("[component-scan] ScannedBeanTwo: @PreDestroy fired!");
	}

}
