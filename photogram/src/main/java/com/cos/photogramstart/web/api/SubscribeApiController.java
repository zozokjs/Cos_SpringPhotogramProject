package com.cos.photogramstart.web.api;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cos.photogramstart.config.auth.PrincipalDetails;
import com.cos.photogramstart.service.SubscribeService;
import com.cos.photogramstart.web.dto.CMRespDto;

import lombok.RequiredArgsConstructor;

//데이터만 리턴하므로 api 컨트롤러라고 이름 지음
@RequiredArgsConstructor
@RestController
public class SubscribeApiController {

	private final SubscribeService subscribeService;
	
	
	//구독 처리
	//A가 B를 구독한다.(로그인 한 A가 B를 구독한다)
	@PostMapping("/api/subscribe/{toUserId}")
	public ResponseEntity<?> subscribe(@AuthenticationPrincipal PrincipalDetails principalDetails, @PathVariable int toUserId){
		
		subscribeService.구독하기(principalDetails.getUser().getId(), toUserId);
		return new ResponseEntity<>(new CMRespDto<>(1, "구독하기 성공", null), HttpStatus.OK);
	}

	
	//구독 해지 처리
	//A가 B를 구독 해지한다.(로그인 한 A가 B를 구독 해지한다)
	@DeleteMapping("/api/subscribe/{toUserId}")
	public ResponseEntity<?> unSubscribe(@AuthenticationPrincipal PrincipalDetails principalDetails, @PathVariable int toUserId){
		
		subscribeService.구독취소하기(principalDetails.getUser().getId(), toUserId);

		return new ResponseEntity<>(new CMRespDto<>(1, "구독취소 성공", null), HttpStatus.OK);
	}

}
