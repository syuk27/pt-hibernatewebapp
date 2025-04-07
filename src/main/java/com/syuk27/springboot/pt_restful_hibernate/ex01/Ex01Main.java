package com.syuk27.springboot.pt_restful_hibernate.ex01;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
import jakarta.persistence.PersistenceContext;

public class Ex01Main {

	@PersistenceContext
	private EntityManager em;
	
	public static void main(String[] args) {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
		
		Ex01Main main = new Ex01Main();
		
		EntityManager em = main.em;
		
		EntityTransaction tx = em.getTransaction();
		tx.begin();
		
		Member member = new Member();
		member.setId(1L);
		member.setName("HelloA");
		
		em.persist(member);
		
		tx.commit();
		
		em.close();
		emf.close();

	}

}
