package com.cos.photogramstart.domain.image;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;

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
public class Image {
	@Id // PK값 설정
	@GeneratedValue(strategy = GenerationType.IDENTITY) // 번호 자동 증가 설정함. 정책은 DB처럼 번호 증가함
	private int id;
	
	private String caption; //나 너무 피곤해;;
	
	private String postImageUrl; //이미지 url임.
	//사진을 받아서, 서버의 특정 폴더에 저장할 것임
	//db에는 그 저장된 경로를 insert할 것임.
	
	/* * 업로드한 사람. 
	 * 1명의 유저는 여러 개의 이미지를 가진다 
	 * 1개의 이미지는 1개의 유저를 가진다. 
	 * 그래서 N : 1의 관계
	 * 
	 * @Entity라고 적었으므로 DB에는 테이블이 생성될텐데, 아래는 USER 오브젝트이므로 FK가 생성된다. 
	 * @JoinColumn으로 FK 이름을 지정함
	 */
	@JoinColumn(name = "userId")
	@ManyToOne
	private User user; 
	
	
	
	//이미지 좋아요, 댓글...
	
	private LocalDateTime createDate;

	@PrePersist // db에 insert 되기 직전에 실행 됨. 직전에 현재 시간을 가져와 넣어줌.
	public void createDate() {
		this.createDate = LocalDateTime.now();
	}

	
	//오브젝트를 console에 출력할 때 문제가 될 수 있으므로 User 부분을 제외함.
	/*	@Override
		public String toString() {
		return "Image [id=" + id + ", caption=" + caption + ", postImageUrl=" + postImageUrl
				+ ", createDate=" + createDate + "]";
		}*/
	
	
	
}
