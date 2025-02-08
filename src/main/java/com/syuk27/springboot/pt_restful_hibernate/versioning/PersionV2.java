package com.syuk27.springboot.pt_restful_hibernate.versioning;

public class PersionV2 {

	private Name name;

	public PersionV2(Name name) {
		super();
		this.name = name;
	}

	public Name getName() {
		return name;
	}

	@Override
	public String toString() {
		return "PersionV2 [name=" + name + "]";
	}
	
}
