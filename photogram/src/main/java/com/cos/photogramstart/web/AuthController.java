package com.cos.photogramstart.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller // IOC 등록 및 파일 리턴하는 컨트롤러라고 명시함
public class AuthController {

	// signin.jsp 파일이 리턴됨
	 @GetMapping("/auth/signin")
	 public String signinForm() {
		 return "auth/signin";
	 }
	 
	 @GetMapping("/auth/signup")
	 public String signupForm() {
		 return "auth/signup";
	 }
	 
	 // 회원가입 버튼을 클릭하면 auth/ signup으로 이동하는데 
	 // return auth / signin이라고 명시 했으므로 리턴되어야 함
	 // 그러나 csrf 토큰이 활성화 되어 있어서 이것이 리턴 안 되고 있다. 
	 /**
	  * 1. 클라이언트가 서버에 회원가입페이지를 요청함
	  * 2. 서버를 감싸고 있는 시큐리티가 form에 난수로 이뤄진 CSRF 토큰을 심고 회원가입페이지를 리턴한다.
	  * 3. 클라리언트가 회원가입 페이지에 작성하고 서버에 요청함
	  * 4. 시큐리티는 방금 심었던 CSRF를 검사한다. 방금 심었던 토큰이 있으면 가입 처리함.
	  * 이를 비활성화하려고 http.csrf.disable()을 했음.
	  * */
	 @PostMapping("/auth/signup")
	 public String signup() {
		 return "auth/signin"; //회원 가입 성공 시 리턴
	 }
	 
	 
}
