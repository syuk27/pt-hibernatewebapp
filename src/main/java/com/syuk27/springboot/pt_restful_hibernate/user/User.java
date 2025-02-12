package com.syuk27.springboot.pt_restful_hibernate.user;

import java.time.LocalDate;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;

@Entity(name = "user_details")
public class User {

	//jakarta.validation.constraints => 유효성 검증 
	
	@Id
	@GeneratedValue
	private Integer id;
	
	@Size(min = 2, message = "이름은 2글자 이상이어야 합니다.")
	@NotNull
	@JsonProperty("user_name") // JSON 출력 시 적용될 필드명
    @JsonAlias({"userName", "usrname"})  // JSON 입력 시 허용할 필드명들
	private String name;
	
	@Past(message = "생일은 현재보다 과거의 날짜이어야 합니다.") //현재보다 과거 
	private LocalDate birthDate;
	
	@OneToMany(mappedBy = "user")
	//jpa에서 일대다(1:N) 관계를 설정할 때 사용, User (1) <-> Post (N), ex) 한 명의 유저(User)는 여러 개의 게시글(Post)을 가질 수 있음
	//{참조 테이블명}_{PK 필드명} 형식으로 외래 키(Foreign Key)를 자동 생성
	//@JoinColumn(name = "외래키_이름") 사용하여 외래 키 이름 변경 가능 
	@JsonIgnore
	private List<Post> posts;
	
	//기본 생성자 추가 (Hibernate 필요)
	public User() { }
	
	public User(Integer id, String name, LocalDate birthDate) {
		super();
		this.id = id;
		this.name = name;
		this.birthDate = birthDate;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public LocalDate getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(LocalDate birthDate) {
		this.birthDate = birthDate;
	}


	public List<Post> getPosts() {
		return posts;
	}

	public void setPosts(List<Post> posts) {
		this.posts = posts;
	}
	
	@Override
	public String toString() {
		return "User [id=" + id + ", name=" + name + ", birthDate=" + birthDate + "]";
	}
}
