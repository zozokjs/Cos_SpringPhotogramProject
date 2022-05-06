package com.cos.photogramstart.web.dto.auth;

import lombok.Data;

//요청하는 dto
//통신할 떄 쓰는 오브젝트

@Data
public class SignupDto {

	private String username;
	private String password;
	private String email;
	private String name;
	
}
