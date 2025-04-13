package com.syuk27.springboot.pt_restful_hibernate.kyh.ex01;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
import jakarta.persistence.PersistenceContext;

public class Ex01JPA2 {

	@PersistenceContext
	private EntityManager em;


	public static void main(String[] args) {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");

		Ex01JPA2 main = new Ex01JPA2();

		EntityManager em = main.em;

		EntityTransaction tx = em.getTransaction();
		tx.begin();

		try {
			Member member = new Member(200L, "member200");
			
			//commit 전 db에 반영됨. jpql 쿼리 실행시 플러시 자동 호출됨. 
			//영속성 컨텍스트(1차 캐시)를 비우지 않음.
			//영속성 컨텍스트의 변경내용을 데이터베이스에 동기화.
			//트랜잭션이라는 작업 단위가 중요 -> 커밋 직전에만 동기화하면 됨. 
			em.flush();
			
			
			//영속성 컨텍스트에서 관리하지 않도록 빼버림. -> 준영속 상태로 전환. 
			em.detach(member);
			
			//영속성 컨텍스트를 완전히 초기화.
			em.clear();
			
			//연속성 컨텍스트를 종료. 
			em.clear();
			
			tx.commit();
			
		} catch (Exception e) {
			tx.rollback();
		} finally {
			em.close();

		}

		emf.close();

	}

}
