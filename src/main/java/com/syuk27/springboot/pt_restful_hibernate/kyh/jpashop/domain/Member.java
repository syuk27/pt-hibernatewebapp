package com.syuk27.springboot.pt_restful_hibernate.kyh.jpashop.domain;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Member {

	@Id @GeneratedValue
	@Column(name = "member_id")
	private Long id;
	
	private String name;
	
	
	private String city;
	
	
	private String street;
	
	
	private String zipcode;
	
	// @OneToMany와 @ManyToOne을 둘 다 선언해야 양방향 관계가 됨. 
	// 연관관계의 주인은 @ManyToOne - 실무에서는 대부분 @ManyToOne만 선언하는 단방향 매핑을 많이 사용. 
	@OneToMany(mappedBy = "member") // 양방향 연관관계의 주인은 Order.class의 member필드. 외래키 있는 클래스가 주인. 
	private List<Order> orders = new ArrayList<>(); // = new ArrayList<>(); -> NullPointerException 방지. JPA/Hibernate 공식 권장 사항.
	
	// 설계는 단방향 매핑으로 완료 해야함. 양방향 매핑은 반대 방향으로 조회 기능이 추가 된 것 뿐. 
	// JPQL에서 역방향으로 탐색할 일이 많음.
	// 단반향 매핑을 잘 하고 양방향은 필요 할 때 추가. 
	// 양방향 세팅, 성능상 db가 아닌 영속성 컨텍스트에서 호출할 때 양방향으로 set 해줘야 함. 
	public void addOrder(Order order) {
        orders.add(order);
        order.setMember(this); // 주인도 같이 설정
    }
	
}
