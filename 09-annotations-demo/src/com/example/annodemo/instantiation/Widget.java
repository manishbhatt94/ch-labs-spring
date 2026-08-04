package com.example.annodemo.instantiation;

/** Plain result type produced by all three instantiation styles below. */
public class Widget {
	private final String origin;

	public Widget(String origin) {
		this.origin = origin;
		System.out.println("[instantiation] {!= Widget#Widget(String orging) =!} -- " + "Widget object created, origin="
				+ origin + ", identityHash=" + System.identityHashCode(this));
	}

	public String getOrigin() {
		return origin;
	}

}
