package com.syuk27.springboot.pt_restful_hibernate.user;

import java.net.URI;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
public class UserResource {
	
//	@Autowired => 1.필드 주입 방식 
	private UserDaoService userDaoService;
	
	// 2.생성자 주입 방식  
	public UserResource(UserDaoService userDaoService) {
		this.userDaoService = userDaoService;
	}
	
	//200 - success
	//201 - created
	//204 - no content
	//401 - unauthorized
	//404 - resource not found
	//400 - server error

	@GetMapping("/find_retrieve")
	public ResponseEntity<List<User>> retrieveAllUsers() {
		return ResponseEntity.ok().body(userDaoService.findAll());
	}
	
	@GetMapping("/find_retrieve/{id}")
	public ResponseEntity<User> retrieveUsersById(@PathVariable int id) {
		
		User user = userDaoService.findOne(id);
		if(user == null) {
			throw new UserNotFoundException("not found id: " + id);
		}
		
		
		return ResponseEntity.ok().body(user);
	}
	
	@PostMapping("/save_users")
	public ResponseEntity<Object> createUsers(@RequestBody User user) {
		try {
			User SavedUser = userDaoService.save(user);
			URI location = ServletUriComponentsBuilder.fromCurrentRequest()
								.path("/{id}")
								.buildAndExpand(SavedUser.getId())
								.toUri();
			return ResponseEntity.created(location).build();
			
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		}
		
	}
}
