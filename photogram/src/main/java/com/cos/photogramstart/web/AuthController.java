package com.cos.photogramstart.web;

import java.util.HashMap;
import java.util.Map;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.cos.photogramstart.domain.user.User;
import com.cos.photogramstart.handler.ex.CustomValidationException;
import com.cos.photogramstart.service.AuthService;
import com.cos.photogramstart.web.dto.auth.SignupDto;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor // 생성자를 만들어줌(  final 필드를 di 하기 위해 명시함  )
@Controller // IOC 등록 및 파일 리턴하는 컨트롤러라고 명시함
public class AuthController {

	private static final Logger log = LoggerFactory.getLogger(AuthController.class);
	
	
	private final AuthService authService;
	//전역변수에 final이 붙이면 초기화를 반드시 해줘야 한다.
	
	
	
	/**
	@Autowired
	private AuthService authService;
	*/
	
	
	/**
	private AuthService authService;
	
	public AuthController(AuthService as) {
		this.authService=as;
	}
	*/
	
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
	 public String signup(@Valid SignupDto signupDto, BindingResult bindingResult) {
		 //@Valid 걸어서 유효성 체크 하게 함...
		 //유효성에서 에러 발생하면 BindingResult 클래스에 담긴다. 그걸 hasErrors()로 다 가져올 수 있음
		 /**
		  * 클래스에 @Controller가 붙어서 파일을 리턴하게 했지만
		  * 메소드에 @ResposeBody를 붙이면 데이터 리턴 됨.
		  * */
		 
		 log.info(signupDto.toString());  //클라이언트가 작성하여 전송한 값을 dto에 담았음.

		 User user = signupDto.toEntity(); 
		 
		// log.info(user.toString());
		 
		authService.회원가입(user);
		// System.out.println(userEntity);
		 return "auth/signin"; //회원 가입 성공 시 리턴
		//return null;

	 }
	 
	 
}
