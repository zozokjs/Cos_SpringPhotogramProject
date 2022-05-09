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

	private boolean pageOwnerState;
	
	private User user;

	private int imageCount;
}
