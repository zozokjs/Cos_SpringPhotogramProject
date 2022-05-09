package com.cos.photogramstart.web.dto.subscribe;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
//구독 정보를 저장하는 dto
public class SubscribeDto {

	private int id;
	private String username;
	private String profileImageUrl; //프로필 사진
	
	// Maria DB는 DB출력에서의 True를 구분하지 못한다.
	private Integer subscribeState; //구독 상태
	private Integer equalUserState; //로그인한 사람인가? 
	
}
