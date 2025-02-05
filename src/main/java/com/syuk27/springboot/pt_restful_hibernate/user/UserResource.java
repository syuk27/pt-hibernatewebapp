package com.syuk27.springboot.pt_restful_hibernate.user;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserResource {
	
//	@Autowired => 1.필드 주입 방식 
	private UserDaoService userDaoService;
	
	// 2.생성자 주입 방식  
	public UserResource(UserDaoService userDaoService) {
		this.userDaoService = userDaoService;
	}

	@GetMapping("find_retrieve")
	public ResponseEntity<List<User>> retrieveAllUsers() {
		return ResponseEntity.ok().body(userDaoService.findAll());
	}
}
