package com.cos.photogramstart.domain.user.subscribe;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import com.cos.photogramstart.domain.user.User;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Entity // JPA를 위함. 디비에 테이블을 생성함
@Data
@NoArgsConstructor // 빈 생성자
@AllArgsConstructor // 모든 생성자
//formUserId와 toUserId는 유니크해야 한다. 39번 영상
@Table(
		uniqueConstraints = { 
				@UniqueConstraint(
						name = "subscribe_uk",  //유니크 컬럼 이름
						columnNames = {"fromUserId","toUserId"} //실제 db에 존재하는 컬럼 이름을 써야 함
							) 
				}
		)
public class Subscribe { // 중간 테이블

	@Id // PK값 설정
	@GeneratedValue(strategy = GenerationType.IDENTITY) // 번호 자동 증가 설정함. 정책은 DB처럼 번호 증가함
	private int id;

	// 구독 함
	@JoinColumn(name = "fromUserId")
	// 기본적으로 db에는 fromUser_id라고 컬럼이 생성되는데, 이게 마음에 안 들어서
	// 별개로 컬럼 이름을 지정함
	@ManyToOne // Subscribe가 Many, User가 One
	private User fromUser;

	// 구독 받음
	@JoinColumn(name = "toUserId")
	@ManyToOne // Subscribe가 Many, User가 One
	private User toUser;

	private LocalDateTime createDate;

	@PrePersist // db에 insert 되기 직전에 실행 됨. 직전에 현재 시간을 가져와 넣어줌.
	public void createDate() {
		this.createDate = LocalDateTime.now();
	}

}
