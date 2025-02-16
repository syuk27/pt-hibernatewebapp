package com.syuk27.springboot.pt_restful_hibernate.user;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.syuk27.springboot.pt_restful_hibernate.jpa.PostRepository;
import com.syuk27.springboot.pt_restful_hibernate.jpa.UserRepository;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/jpa")
public class UserJpaResource {

	/**
	 * 무한 트랜잭션 방지 (잘 안됨 나중에 확인 필요) private final ConcurrentMap<String, Bucket> cache
	 * = new ConcurrentHashMap<>();
	 * 
	 * private Bucket getBucket(String clientIp) { return
	 * cache.computeIfAbsent(clientIp, key -> Bucket.builder()
	 * .addLimit(Bandwidth.classic(5, Refill.greedy(5, Duration.ofMinutes(1)))) //
	 * 1분에 최대 5번 요청 가능 .build()); }
	 */

//	@Autowired => 1.필드 주입 방식 
	private UserRepository userRepository;

	private PostRepository postRepository;

	// 2.생성자 주입 방식
	public UserJpaResource(UserRepository userRepository, PostRepository postRepository) {
		this.userRepository = userRepository;
		this.postRepository = postRepository;
	}
	
	@GetMapping("/basic_token_login")
	public ResponseEntity<?> basicTokenLogin() {
		return ResponseEntity.ok().build();
	}

	// 200 - success
	// 201 - created
	// 204 - no content
	// 401 - unauthorized
	// 404 - resource not found
	// 400 - server error

	@GetMapping("/find_users")
	public ResponseEntity<List<User>> retrieveAllUsers() {
		return ResponseEntity.ok().body(userRepository.findAll());
	}

	@GetMapping("/find_users/{id}")
	public ResponseEntity<Optional<User>> retrieveUsersById(@PathVariable int id) {

		Optional<User> user = userRepository.findById(id);
		if (user.isEmpty()) {
			throw new UserNotFoundException("not found id: " + id);
		}

		return ResponseEntity.ok().body(user);
	}

	@DeleteMapping("/del_users/{id}")
	public ResponseEntity<Object> deleteUsers(@PathVariable int id) {
		try {
			userRepository.deleteById(id);
			return ResponseEntity.ok().build();
			
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		}
	}

	// @valid spring-boot-starter-validation 추가
	@PostMapping("/save_users")
	public ResponseEntity<Object> createUsers(@Valid @RequestBody User user) {
		try {
			User SavedUser = userRepository.save(user);
			URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
					.buildAndExpand(SavedUser.getId()).toUri();
			return ResponseEntity.created(location).build();

		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		}

	}
	
	@PutMapping("/modify_users/{id}")
	public ResponseEntity<Object> modifyUser(@Valid @RequestBody User modiUser, @PathVariable int id) {
		
		try {
			Optional<User> user = userRepository.findById(id);
			if (user.isEmpty()) {
				throw new UserNotFoundException("not found id: " + id);
			}
			user.orElseThrow().setName(modiUser.getName());
			user.orElseThrow().setBirthDate(modiUser.getBirthDate());
			
			userRepository.save(user.orElseThrow());
			
			return ResponseEntity.ok().build();
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		}
	}
	

	@GetMapping("/users/{id}/find_posts")
	public ResponseEntity<List<Post>> retrievePostsForAllUsers(@PathVariable int id) {

		Optional<User> user = userRepository.findById(id);
		if (user.isEmpty()) {
			throw new UserNotFoundException("not found id: " + id);
		}

		return ResponseEntity.ok().body(user.get().getPosts());
	}

	@PostMapping("/users/{id}/save_posts")
	public ResponseEntity<Object> createPostsForUsers(@Valid @RequestBody Post post, @PathVariable int id) {

		Optional<User> user = userRepository.findById(id);
		if (user.isEmpty()) {
			throw new UserNotFoundException("not found id: " + id);
		}

		post.setUser(user.get());

		Post savedPost = postRepository.save(post);

		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(savedPost.getId())
				.toUri();
		return ResponseEntity.created(location).build();

	}

}
