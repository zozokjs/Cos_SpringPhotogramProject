package com.cos.photogramstart.web;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.cos.photogramstart.config.auth.PrincipalDetails;
import com.cos.photogramstart.domain.user.User;
import com.cos.photogramstart.service.UserService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
public class UserController {

	
	private final UserService userService;
	
	
	//사용자 정보 페이지
	@GetMapping("/user/{id}")
	public String profile(@PathVariable int id, Model model) {
		
		User userEntity =   userService.회원프로필(id);
		
		model.addAttribute("user",userEntity);
		
		return "user/profile";
	}
	
	
	//회원 정보 수정 처리하기 위해 수정 페이지로 이동함
	@GetMapping("/user/{id}/update")
	public String update(@PathVariable int id , @AuthenticationPrincipal PrincipalDetails principalDetails) {
		
		//SecurityContextHolder 안에 있는 SecurityContext 안에 있는 Authentication 객체에 접근 하는 방법
		System.out.println("세션 정보 : "+principalDetails.getUser());
		/**
		 * 	 이렇게만 해도 update.jsp에서 
		 * ${principal.user.username}로 받아올 수 있다.
		 * 물론 header에서 	<sec:authentication property="principal" var = "principal"/>로 얻어왓기에 가능
		 * */

		
		
		/**@AuthenticationPrincipal 없이 찾는 방법 */
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();		
		PrincipalDetails otherPrincipalDetails= (PrincipalDetails) auth.getPrincipal();		
		System.out.println("(다른 방식으로)세션 정보 : "+otherPrincipalDetails.getUser());
				
		return "user/update";
	}
	
}
