package com.syuk27.springboot.pt_restful_hibernate.versioning;

public class PersionV1 {

	private String name;

	public PersionV1(String name) {
		super();
		this.name = name;
	}

	public String getName() {
		return name;
	}

	@Override
	public String toString() {
		return "PersionV1 [name=" + name + "]";
	}
	
}
