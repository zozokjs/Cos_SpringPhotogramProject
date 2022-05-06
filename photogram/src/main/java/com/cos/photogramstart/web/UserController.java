package com.cos.photogramstart.web;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.cos.photogramstart.config.auth.PrincipalDetails;

@Controller
public class UserController {

	@GetMapping("/user/{id}")
	public String profile(@PathVariable int id) {
		return "user/profile";
	}
	
	
	@GetMapping("/user/{id}/update")
	public String update(@PathVariable int id , @AuthenticationPrincipal PrincipalDetails principalDetails) {
		
		//SecurityContextHolder 안에 있는 SecurityContext 안에 있는 Authentication 객체에 접근 하는 방법
		System.out.println("세션 정보 : "+principalDetails.getUser());
		
		
		
		/**@AuthenticationPrincipal 없이 찾는 방법 */
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		
		PrincipalDetails otherPrincipalDetails= (PrincipalDetails) auth.getPrincipal();
		
		System.out.println("(다른 방식으로)세션 정보 : "+otherPrincipalDetails.getUser());
		
		return "user/update";
	}
	
}
