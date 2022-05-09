package com.cos.photogramstart.web.dto.user;

import com.cos.photogramstart.domain.user.User;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
//사용자 정보 페이지에서, 로그인한 사용자가 자기 자신의 페이지에 왔는지 다른 사람 페이지에 왔는지 구분하기 위해 사용
public class UserProfileDto {

	//프로필 페이지 주인 여부
	private boolean pageOwnerState;
	
	private User user;

	//프로필 페이지의 이미지 수
	private int imageCount;
	
	//구독 상태
	private boolean subscribeState;
	
	//구독자 수
	private int subscribeCount;
	
}
