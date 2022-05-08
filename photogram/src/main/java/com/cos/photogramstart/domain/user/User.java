package com.cos.photogramstart.domain.user;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;

import com.cos.photogramstart.domain.image.Image;


import lombok.AllArgsConstructor;
import lombok.Builder;
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

	 //48번 영상 13분부터
	 // 1명의 User는 여러 개의 Image를 가질 수 있다.
	 // Image 클래스의 변수 user를 적었음
	 @OneToMany(mappedBy = "user" , fetch = FetchType.LAZY) 
	 // => 나는 연관 관계의 주인이 아니다. 그러므로 테이블에 컬럼을 만들지마
	 //  => User를 Select 할 때, 해당 User의 id로 등록된 image들을 전부 가져와
	 //  => Lazy = 유저를 Select 할 때 해당 User의 id로 등록된 image들을 가져오지 마.
	 //                    대신, getImage() 함수가 호출될 때 가져와
	 //  => Eager = 유저를 Select 할 때 해당 User의 id로 등록된 image들을  전부 Join해서 가져 와
	 private List<Image> images; //양방향 매핑
	 
	 
	private LocalDateTime createDate;
	
	@PrePersist // db에 insert 되기 직전에 실행 됨.  직전에 현재 시간을 가져와 넣어줌.
	public void createDate() {
		this.createDate = LocalDateTime.now();		
	}
	
	
}
