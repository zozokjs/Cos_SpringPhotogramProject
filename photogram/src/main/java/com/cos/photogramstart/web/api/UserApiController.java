package com.cos.photogramstart.web.api;

import java.util.HashMap;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cos.photogramstart.config.auth.PrincipalDetails;
import com.cos.photogramstart.domain.user.User;
import com.cos.photogramstart.handler.ex.CustomValidationApiException;
import com.cos.photogramstart.service.UserService;
import com.cos.photogramstart.web.dto.CMRespDto;
import com.cos.photogramstart.web.dto.user.UserUpdateDto;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
public class UserApiController {

	
	private final UserService userService;
	
	
	@PutMapping("/api/user/{id}")
	public CMRespDto<?> update(
			@PathVariable int id,  
			@Valid UserUpdateDto userUpdateDto, 
			BindingResult bindingResult,
			@AuthenticationPrincipal PrincipalDetails principalDetails) {
		// ★★★★★ BindingResult 객체는 반드시 @Valid가 적혀있는 파라메터 다음에 적혀 있어야 동작한다.
		// @AuthenticationPrincipal PrincipalDetails principalDetails로 세션 정보에 접근함.
		
		System.out.println("hchc  < "+userUpdateDto);
		
		//회원 정보 수정 시 유효성 검사 오류 있을 때
		if(bindingResult.hasErrors()) {	
			 Map<String, String> errorMap = new HashMap<>();			 
			 for(FieldError error : bindingResult.getFieldErrors()) {
				 
				 errorMap.put(error.getField(), error.getDefaultMessage());		

				 System.out.println("======================");				 
				 System.out.println(error.getDefaultMessage());  
				 System.out.println("======================");		
			 }
			throw new CustomValidationApiException("유효성 검사 실패함", errorMap); 
			//ControllerExceptionHandler에서 모든 Exception을 가로채게 했으므로 그 쪽 클래스가 발동함.	 
		 }else {
			//회원 정보 수정 시 유효성 검사 통과 시 
			 /**
				 * 이 메소드의 실행 결과로 더티 채킹이 이뤄져서 업데이트 됨.
				 * 이 메소드 안에서는 (
				 * 1. id 찾음으로써 영속화 
				 * 2. 영속화된 데이터를 수정 데이터로 덮어 씌움
				 * 3. 영속화된 데이터를 리턴
				 * 4. 1번과 2번의 데이터가 별개이므로 채킹이 이뤄져서 db 업데이트 발생)
				 * 이 이뤄진다. 다만, 시큐리티 세션은 변경이 안 되어 있음.
				 * */	
				User userEntity 
				= userService.회원수정(id, userUpdateDto.toEntity());
				
				
				principalDetails.setUser(userEntity); //세션 정보를 변경 함(반드시 해야 함)
				
				return new CMRespDto<>(1, "회원수정완료",userEntity);
		 }
		
		

	}
	
	
}