package com.syuk27.springboot.pt_restful_hibernate.user;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

import org.springframework.stereotype.Service;


// @Component → 특정한 역할 없이 Spring에서 관리해야 하는 일반적인 Bean
// @Service → 비즈니스 로직을 담당하는 서비스 계층 클래스에 사용
// @Service는 @Component의 특수한 형태로, 의미적으로 더 명확하게 표현하기 위해 사용
// @Service는 AOP(트랜잭션 등)에서 유리한 경우가 많음
// 서비스 로직을 담당하는 클래스라면 @Service를 쓰고, 특정 계층이 아닌 일반적인 Bean이라면 @Component를 사용

@Service
public class UserDaoService {
	private static List<User> users = new ArrayList<>();
	
	private static Integer usersCount = 0;
	
	static {
		users.add(new User(++usersCount, "Adam", LocalDate.now().minusYears(30)));
		users.add(new User(++usersCount, "Adam2", LocalDate.now().minusYears(32)));
		users.add(new User(++usersCount, "Adam3", LocalDate.now().minusYears(34)));
	}
	
	public List<User> findAll() {
		return users;
	}
	
	public User findOne(int id) {
		Predicate<? super User> predicate = user -> user.getId().equals(id);
		return users.stream().filter(predicate).findFirst().orElse(null);
	}
	
	public void deleteUser(int id) throws Exception {
		Predicate<? super User> predicate = user -> user.getId().equals(id);
		boolean removed = users.removeIf(predicate);
		
		if (!removed) {
	        throw new Exception("사용자를 찾을 수 없습니다: " + id);
	    }
	}
	
	public User save(User user) {
		user.setId(++usersCount);
		users.add(user);
		return user;
	}
}
