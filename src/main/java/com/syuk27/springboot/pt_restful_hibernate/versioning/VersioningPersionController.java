package com.syuk27.springboot.pt_restful_hibernate.versioning;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class VersioningPersionController {

	/** 1. URI Versioning - 트위터 V */
	@GetMapping("/v1/persion")
	public PersionV1 getFirstVersionOfPersion() {
		return new PersionV1("Bob Charlie");
	}
	
	@GetMapping("/v2/persion")
	public PersionV2 getSecondVersionOfPersion() {
		return new PersionV2(new Name("Bob", "Charlie"));
	}
	
	/** 2. Request Parameter Versioning - 아마존 V */
	@GetMapping(path = "/persion", params = "version=1")
	public PersionV1 getFirstVersionOfPersionRequestParam() {
		return new PersionV1("Bob Charlie");
	}
	
	@GetMapping(path = "/persion", params = "version=2")
	public PersionV2 getSecondVersionOfPersionRequestParam() {
		return new PersionV2(new Name("Bob", "Charlie"));
	}
	
	/** 3. (Custom) headers Versioning - 마소 V */
	/** X-API-VERSION : 1 */
	@GetMapping(path = "/persion/header", headers = "X-API-VERSION=1")
	public PersionV1 getFirstVersionOfPersionHeader() {
		return new PersionV1("Bob Charlie");
	}
	
	@GetMapping(path = "/persion/header", headers = "X-API-VERSION=2")
	public PersionV2 getSecondVersionOfPersionHeader() {
		return new PersionV2(new Name("Bob", "Charlie"));
	}
	
	/** 4. Media type Versioning - git V */
	/** Accept : application/vnd.company.app-v1+json */
	@GetMapping(path = "/persion/accept", produces = "application/vnd.company.app-v1+json")
	public PersionV1 getFirstVersionOfPersionAcceptHeader() {
		return new PersionV1("Bob Charlie");
	}
	
	@GetMapping(path = "/persion/accept", produces = "application/vnd.company.app-v2+json")
	public PersionV2 getSecondVersionOfPersionAcceptHeader() {
		return new PersionV2(new Name("Bob", "Charlie"));
	}
}
