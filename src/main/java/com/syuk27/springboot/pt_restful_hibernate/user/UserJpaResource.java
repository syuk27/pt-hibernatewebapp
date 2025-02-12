package com.syuk27.springboot.pt_restful_hibernate.user;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.syuk27.springboot.pt_restful_hibernate.jpa.PostRepository;
import com.syuk27.springboot.pt_restful_hibernate.jpa.UserRepository;

import jakarta.validation.Valid;

@RestController
public class UserJpaResource {
	
//	@Autowired => 1.필드 주입 방식 
	private UserRepository userRepository;
	
	private PostRepository postRepository;
	
	// 2.생성자 주입 방식  
	public UserJpaResource(UserRepository userRepository, PostRepository postRepository) {
		this.userRepository = userRepository;
		this.postRepository = postRepository;
	}
	
	//200 - success
	//201 - created
	//204 - no content
	//401 - unauthorized
	//404 - resource not found
	//400 - server error

	@GetMapping("/jpa/find_users")
	public ResponseEntity<List<User>> retrieveAllUsers() {
		return ResponseEntity.ok().body(userRepository.findAll());
	}
	
	@GetMapping("/jpa/find_users/{id}")
	public ResponseEntity<Optional<User>> retrieveUsersById(@PathVariable int id) {
		
		Optional<User> user = userRepository.findById(id);
		if(user.isEmpty()) {
			throw new UserNotFoundException("not found id: " + id);
		}
		
		
		return ResponseEntity.ok().body(user);
	}
	
	@DeleteMapping("/jpa/del_users/{id}")
	public ResponseEntity<User> deleteUsers(@PathVariable int id) throws Exception {
		
		userRepository.deleteById(id);
		return ResponseEntity.ok().build();
	}
	
	// @valid spring-boot-starter-validation 추가 
	@PostMapping("/jpa/save_users")
	public ResponseEntity<Object> createUsers(@Valid @RequestBody User user) {
		try {
			User SavedUser = userRepository.save(user);
			URI location = ServletUriComponentsBuilder.fromCurrentRequest()
								.path("/{id}")
								.buildAndExpand(SavedUser.getId())
								.toUri();
			return ResponseEntity.created(location).build();
			
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		}
		
	}
	
	@GetMapping("/jpa/find_users/{id}/posts")
	public ResponseEntity<List<Post>> retrievePostsForAllUsers(@PathVariable int id) {
		
		Optional<User> user = userRepository.findById(id);
		if(user.isEmpty()) {
			throw new UserNotFoundException("not found id: " + id);
		}
		
		
		return ResponseEntity.ok().body(user.get().getPosts());
	}
	
	@PostMapping("/jpa/users/{id}/save_posts")
	public ResponseEntity<Object> createPostsForUsers(@Valid @RequestBody Post post, @PathVariable int id) {
		
		Optional<User> user = userRepository.findById(id);
		if(user.isEmpty()) {
			throw new UserNotFoundException("not found id: " + id);
		}
		
		post.setUser(user.get());
		
		Post savedPost = postRepository.save(post);
		
		URI location = ServletUriComponentsBuilder.fromCurrentRequest()
				.path("/{id}")
				.buildAndExpand(savedPost.getId())
				.toUri();
		return ResponseEntity.created(location).build();
		
	}
	
}
