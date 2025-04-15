package com.syuk27.springboot.pt_restful_hibernate.kyh.jpashop.domain;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "orders")
public class Order {

	@Id @GeneratedValue
	@Column(name = "order_id")
	private Long id;
	
//	@Column(name = "member_id")
//	private Long memverId;
	
	@ManyToOne
	@JoinColumn(name = "member_id")
	private Member member;
	
	@OneToMany(mappedBy = "order")
	private List<OrderItem> orderItems = new ArrayList<>();
	
	private LocalDateTime orderDate;
	
	@Enumerated(EnumType.STRING)
	private OrderStatus status;
	
	// @OneToMany와 @ManyToOne을 둘 다 선언해야 양방향 관계가 됨. 
	// 연관관계의 주인은 @ManyToOne - 실무에서는 대부분 @ManyToOne만 선언하는 단방향 매핑을 많이 사용. 
	// 양방향 매핑 (필수 아님) 주로 JPQL 사용시 사용. 
	public void addOrderItem(OrderItem orderItem) {
		orderItems.add(orderItem);
		orderItem.setOrder(this);
	}
	
	// 양방향 세팅, 성능상 db가 아닌 영속성 컨텍스트에서 호출할 때 양방향으로 set 해줘야 함. 
//	public void changeMember(Member member) {
//		this.member = member;
//		member.getOrders().add(this);
//	}
	
}
