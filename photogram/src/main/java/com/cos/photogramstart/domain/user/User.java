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
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Entity // JPA를 위함. 디비에 테이블ㅇ을 생성함
@Data
@NoArgsConstructor // 빈 생성자
@AllArgsConstructor // 모든 생성자
public class User {

	@Id // PK값 설정
	@GeneratedValue(strategy = GenerationType.IDENTITY) // 번호 자동 증가 설정함. 정책은 DB처럼 번호 증가함
	private int id;

	@Column(length = 100, unique = true)
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

	
	
	/**
	 * 한 번에 가져오는 게 많다면 Lazy를, 적다면 Eager을 쓰는 게 좋음
	 * User를 select 할 때, User가 올린 모든 image를 select 할텐데,
	 * 이것이 EAGER 전략이었다면 
	 * User가 올린 무수한 image들을 가져와야 함. 그럴 필요까진 없으니 Lazy를 쓴다.
	 * */
	// 48번 영상 13분부터
	// 1명의 User는 여러 개의 Image를 가질 수 있다.
	// Image 클래스의 변수 user를 적었음
	@OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
	// => 나는 연관 관계의 주인이 아니다. 그러므로 테이블에 컬럼을 만들지마
	// => User를 Select 할 때, 해당 User의 id로 등록된 image들을 전부 가져와
	// => Lazy = 유저를 Select 할 때 해당 User의 id로 등록된 image들을 가져오지 마.
	// 대신, getImage() 함수가 호출될 때 가져와
	// => Eager = 유저를 Select 할 때 해당 User의 id로 등록된 image들을 전부 Join해서 가져 와
	/**
	 * @JsonIgnoreProperties({"user"})
	 * User 오브젝트가 응답될 때 MessageConverter에 의해 getter 함수가 호출되고
	 *  JSON으로 파싱 되는데, 
	 *  이걸 붙어 놓여믄 images 객체 내부의 user라는 변수명을 가진 필드는 
	 *  getter 함수 호출을 제외하라고 명시하는 것 -> 무한 참조 방지됨
	 * */	
	@JsonIgnoreProperties({"user"})
	private List<Image> images; // 양방향 매핑

	private LocalDateTime createDate;

	@PrePersist // db에 insert 되기 직전에 실행 됨. 직전에 현재 시간을 가져와 넣어줌.
	public void createDate() {
		this.createDate = LocalDateTime.now();
	}

		@Override
		public String toString() {
			return "User [id=" + id + ", username=" + username + ", password=" + password + ", name=" + name + ", website="
					+ website + ", bio=" + bio + ", email=" + email + ", phone=" + phone + ", gender=" + gender
					+ ", profileImageUrl=" + profileImageUrl + ", role=" + role + ", images=createDate=" + createDate + "]";
		}
	
}
