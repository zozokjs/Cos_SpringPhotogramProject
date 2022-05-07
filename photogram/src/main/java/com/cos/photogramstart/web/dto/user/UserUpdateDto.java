package com.cos.photogramstart.web.dto.user;

import com.cos.photogramstart.domain.user.User;

import lombok.Data;

//회원 수정 완료 시, 수정 된 데이터를 db에 던지기 위해 임시 저장하는 오브젝트
// update.jsp -> 버튼 클릭 -> UserApiController 여기서 쓰임.

@Data
public class UserUpdateDto {

	private String name;  //필수
	private String password; //필수
	private String website;
	private String bio;
	private String phone;
	private String gender;
	
	// 좀 위허험함. 코드 수정 해야 함
	public User toEntity() {
		return User.builder()
				.name(name)
				.password(password) 
				// 사용자가 비번을 안 적었다면 비번은 공백으로 들어 갈 것임. 그래서 db에는 공백이 들어간다. validation 체크 해야 함
				.website(website)
				.bio(bio)
				.phone(phone)
				.gender(gender)
				.build();	
		}	

}
