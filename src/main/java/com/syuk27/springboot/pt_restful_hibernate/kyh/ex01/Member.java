package com.syuk27.springboot.pt_restful_hibernate.kyh.ex01;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.persistence.Transient;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor // 파라미터 포함한 생성자 -> jpa 필수 아님. 
@NoArgsConstructor // 파라미터 없는 기본 생성자 == public Member() {} -> jpa에서 필수. 	
@Entity //JPA가 관리한다는 속성.
//@Table(name="mbr")
//@Table(uniqueConstraints = ) @Column에서 유니크 설정 안하고 여기서 함 (유니크 이름 설정 가능하기 때문) 
@SequenceGenerator(name="member_seq_generator", sequenceName = "member_seq") //allocationSize 시퀀스 한 번 호출에 증가하는 수(성능 최적화) 기본값 50. 
public class Member {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "member_seq_generator")
	private Long id;
	
	@Column(length = 10, nullable = false) // unique = true,  -> 실무에서 사용x 이름 설정 못하기 때문 대신 @Table(uniqueConstraints = ) 사용. 
	private String name;
	
	private Integer age;
	
	//EnumType.ORDINAL - enum 순서를 데이터베이스에 저장. -> 기본 설정임. -> 사용 하면 안됨. enum 순서로 숫자가 들어가기 때문에 관리 불가능함.  
	//EnumType.STRING - enum 이름을 데이터베이스에 저장. 
	@Enumerated(EnumType.STRING)
	private RoleType roleTyle;
	
	//과거버전임 -> 현재버전은 어노테이션 없이 private LocalDateTime createdDate; 사용, 둘 다 type은 timestamp로 생성됨. 
	@Temporal(TemporalType.TIMESTAMP)
	private Date createdDate;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date lastModifiedDate;
	
	@Lob
	private String description;
	
	@Transient // 메모리에서만 사용하고 실제 db와 매핑 안함. 
	private int temp;

}
