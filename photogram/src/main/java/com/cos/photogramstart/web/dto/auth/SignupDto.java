package com.cos.photogramstart.web.dto.auth;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.cos.photogramstart.domain.user.User;

import lombok.Data;

//요청하는 dto
//통신할 떄 쓰는 오브젝트
//그렇기 때문에 여기서 Validation 체크 한다.
@Data
public class SignupDto {

	//@Max(20) //Validation 라이브러리. 제대로 동작 안 함... 
	@Size(min = 2, max = 20)  //문자열 검사하기엔 max 보다 size가 나은 듯 함.
	@NotBlank//Validation 라이브러리.
	private String username;
	
	@NotBlank 
	private String password;
	@NotBlank
	private String email;
	@NotBlank
	private String name;
	
	
	public User toEntity() {
		return User.builder()
				.username(username)
				.password(password)
				.email(email)
				.name(name)
				.build();
	}
	
}
