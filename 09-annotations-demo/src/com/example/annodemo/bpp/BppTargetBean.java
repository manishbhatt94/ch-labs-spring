package com.example.annodemo.bpp;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Component;

/** The bean whose initialization gets wrapped by both BPPs above. */
@Component("bppTargetBean")
public class BppTargetBean {

	public BppTargetBean() {
		System.out.println("\n[bpp] BppTargetBean: constructor\n");
	}

	@PostConstruct
	public void init() {
		System.out.println("\n[bpp] BppTargetBean: @PostConstruct init()    <-- runs BETWEEN the two BPP phases\n");
	}

}
