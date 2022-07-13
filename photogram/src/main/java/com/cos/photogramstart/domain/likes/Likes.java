package com.cos.photogramstart.domain.likes;

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

import com.cos.photogramstart.domain.image.Image;
import com.cos.photogramstart.domain.user.User;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Entity // JPA를 위함. 디비에 테이블을 생성함
@Data
@NoArgsConstructor // 빈 생성자
@AllArgsConstructor // 모든 생성자
//imageId와 userId는 유니크해야 한다. 39번 영상
//ex) imageId가 1이고, userId가 2일 떄,
//imageId가 1이고, userId가 2인 값은 다시 올 수 없다.
@Table(
		uniqueConstraints = { 
				@UniqueConstraint(
						name = "likes_uk",  //유니크 컬럼 이름
						columnNames = {"imageId","userId"} //실제 db에 존재하는 컬럼 이름을 써야 함
							) 
				}
		)
//마리아 db에서는 like가 키워드라서 like라는 테이블은 안 만들어짐
public class Likes {
	
	@Id // PK값 설정
	@GeneratedValue(strategy = GenerationType.IDENTITY) // 번호 자동 증가 설정함. 정책은 DB처럼 번호 증가함
	private int id;
	
	//어떤 이미지를 누가 좋아하는가?
	
	//@JsonIgnoreProperties({"likes"})
	@JoinColumn(name = "imageId")
	@ManyToOne //기본 전력은 EAGER이다.
	//하나의 이미지는 여러 개의 like를 가진다.
	private Image image;
	
	
	@JsonIgnoreProperties({"images"})
	@JoinColumn(name = "userId")
	@ManyToOne
	//한 명의 유저는 여러 개의 like를 가진다.
	private User user;
	
	private LocalDateTime createDate;

	@PrePersist // db에 insert 되기 직전에 실행 됨. 직전에 현재 시간을 가져와 넣어줌.
	public void createDate() {
		this.createDate = LocalDateTime.now();
	}
}
