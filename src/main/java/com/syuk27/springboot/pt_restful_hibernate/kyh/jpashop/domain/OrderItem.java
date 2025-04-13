package com.syuk27.springboot.pt_restful_hibernate.kyh.jpashop.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class OrderItem {

	@Id @GeneratedValue
	@Column(name = "order_item_id")
	private Long id;
	
	@Column(name = "order_id")
	private Long orderId;
	
	@Column(name = "item_id")
	private Long itemId;
	
	private Long orderPrice;
	
	private Long count;
}
