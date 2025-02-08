package com.syuk27.springboot.pt_restful_hibernate.filtering;

import com.fasterxml.jackson.annotation.JsonIgnore;

//@JsonIncludeProperties("field1") // field1만 표시
//@JsonIgnoreProperties({"field1", "field3"}) // field1, field3 표시안함 
//@JsonFilter(value = "SomeBeanFilter") // 동적 필터링 => MappingJacksonValue
public class SomeBean {
	private String field1;
	
	@JsonIgnore // 비밀번호 등 숨기고 싶을 때 
	private String field2;
	private String field3;
	
	public SomeBean(String field1, String field2, String field3) {
		super();
		this.field1 = field1;
		this.field2 = field2;
		this.field3 = field3;
	}

	public String getField1() {
		return field1;
	}

	public String getField2() {
		return field2;
	}

	public String getField3() {
		return field3;
	}
	
}
