package com.cos.photogramstart.domain.user;

import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import org.springframework.transaction.annotation.Transactional;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.CustomLog;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Entity //JPA를 위함. 디비에 테이블ㅇ을 생성함
@Data
@NoArgsConstructor // 빈 생성자
@AllArgsConstructor  //모든 생성자 
public class User {

	@Id // PK값 설정
	@GeneratedValue(strategy = GenerationType.IDENTITY) // 번호 자동 증가 설정함. 정책은 DB처럼 번호 증가함
	private int id;
	
	
	@Column(length = 20, unique = true)
	private String username;
	
	@Column(nullable = false)
	private String password;
	
	@Column(nullable = false)	
	private String name;
	
	private String website; // 웹 사이트
	private String bio; // 자기 소개

	@Column(nullable = false)
	private String email;
	private String phone;
	private String gender;
	
	 private String profileImageUrl;
	 
	 private String role; // 권한
	
	private LocalDateTime createDate;
	
	
	@PrePersist // db에 insert 되기 직전에 실행 됨.  직전에 현재 시간을 가져와 넣어줌.
	public void createDate() {
		this.createDate = LocalDateTime.now();		
	}
	
	
}
