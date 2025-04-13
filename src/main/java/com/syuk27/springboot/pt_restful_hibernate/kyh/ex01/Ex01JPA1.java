package com.syuk27.springboot.pt_restful_hibernate.kyh.ex01;

import java.util.List;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
import jakarta.persistence.PersistenceContext;

public class Ex01JPA1 {

	@PersistenceContext
	private EntityManager em;

	/**
	 * 
	 * 엔티티 매니저 팩토리는 하나만 생성해서 애플리케이션 전체에서 공유
	 * 
	 * 엔티티 매니저는 스레드간 공유x (사용하고 버려야 함)
	 * 
	 * JPA의 모든 데이터 변경은 트랜잭션 안에서 실행
	 * 
	 * 자바 컬렉션처럼 사용하기 위한 ORM
	 * 
	 */

	public static void main(String[] args) {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");

		Ex01JPA1 main = new Ex01JPA1();

		EntityManager em = main.em;

		EntityTransaction tx = em.getTransaction();
		tx.begin();

		try {

			//엔티티를 생성한 상태(비영속) 
//			Member member = new Member();
//			member.setId(1L);
//			member.setName("HelloA");
			
			//엔티티를 영속한 상태 1차 캐시에 저장
//			em.persist(member);

			//1차 캐시에서 조회 -> 없으면 DB에서 조회 -> 1차 캐시에 저장 -> 반환 
			//JPA는 트랜잭션 단위이기 때문에 트랜잭션이 종료되면 영속성이 모두 종료됨
			//종료되면 1차 캐시도 날라가기 때문에 성능 이점은 크게 없음 복잡한 로직에서 도움이 될 수도 있음 ( 같은 쿼리 여러번 호출 등 )
			//사용자 동시 접근시 서로 다른 트랜잭션 사용하기 때문에 각자 다름 영속성 컨섹스트를 가지기 때문에 성능 이점은 크게 없다. 
			Member findMember1 = em.find(Member.class, 1L);
			
			//자바 컬렉션 처럼 값만 바꿔도 update됨 트랜잭션 필수 
			findMember1.setName("HelloB");
			
			//findMember1 == findMember2 영속 엔티티의 동일성 보장 (1차 캐시에서 반복 읽기)
			Member findMember2 = em.find(Member.class, 1L);

			/** JPQL */
			List<Member> result = em.createQuery("SELECT m FORM member AS m", Member.class)
					.setFirstResult(1)
					.setMaxResults(10)
					.getResultList();
			
			
			//커밋시 commit() -> 쓰기 지연 sql 저장소에 저장된 데이터들을 -> flush 실제 DB에 저장 -> commit  
			tx.commit();
			
		} catch (Exception e) {
			tx.rollback();
		} finally {
			em.close();

		}

		emf.close();

	}

}
