package com.cos.photogramstart.web.api;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.cos.photogramstart.config.auth.PrincipalDetails;
import com.cos.photogramstart.domain.user.User;
import com.cos.photogramstart.handler.ex.CustomValidationApiException;
import com.cos.photogramstart.service.SubscribeService;
import com.cos.photogramstart.service.UserService;
import com.cos.photogramstart.web.dto.CMRespDto;
import com.cos.photogramstart.web.dto.subscribe.SubscribeDto;
import com.cos.photogramstart.web.dto.user.UserUpdateDto;
import com.nimbusds.oauth2.sdk.Response;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
public class UserApiController {

	
	private final UserService userService;
	
	private final SubscribeService subscribeService;
	
	
	//profile.jsp 및 profile.js에서 profileImageUpload()
	//프로필 사진 업로드.. 데이터를 받는 함수임/
	//★함수의 매개변수 중에서 profileImageFile는 jsp에 name에 명시된 것을 그대로 들고와야 함.
	@PutMapping("/api/user/{principalId}/profileImageUrl")
	public ResponseEntity<?> profileImageUrlUpdate(
			@PathVariable int principalId, MultipartFile profileImageFile,
			@AuthenticationPrincipal PrincipalDetails principalDetails){
		
		//사진이 바뀌었다면, 세션 값도 바뀌어야 함.
		User userEntiry =  
		userService.회원프로필사진변경(principalId, profileImageFile);
		principalDetails.setUser(userEntiry);
		
		return new ResponseEntity<>(new CMRespDto<>(1, "프로필 사진 변경 되었습니다",null), HttpStatus.OK);
		
		
		//사진이 정상적으로 바뀌었습니다.
	
	}
	
	
	
	
	
	//pageUserId가 구독하고 있는 모든 정보 표시
	@GetMapping("/api/user/{pageUserId}/subscribe")
	public ResponseEntity<?> subscribeList(
			@PathVariable int pageUserId, 
			@AuthenticationPrincipal PrincipalDetails principalDetails){
		List<SubscribeDto> subscribeDto = subscribeService.구독리스트(principalDetails.getUser().getId(), pageUserId);
		
		return new ResponseEntity<>(new CMRespDto<>(1, "구독자 정보 호출 성공", subscribeDto), HttpStatus.OK );
	}
	
	
	
	@PutMapping("/api/user/{id}")
	public CMRespDto<?> update(
			@PathVariable int id,  
			@Valid UserUpdateDto userUpdateDto, 
			BindingResult bindingResult,
			@AuthenticationPrincipal PrincipalDetails principalDetails) {
		// ★★★★★ BindingResult 객체는 반드시 @Valid가 적혀있는 파라메터 다음에 적혀 있어야 동작한다.
		// @AuthenticationPrincipal PrincipalDetails principalDetails로 세션 정보에 접근함.
		
		System.out.println("hchc  < "+userUpdateDto);
		
		
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
			/*			
			 * 응답 시 userEntity의 모든 Getter 함수가 호출되고 JSON으로 파싱되어 응답한다
	 		* 	userEntity 응답 시, JSON으로 파싱 된다.
	 		*  1. MessageConverter가 모든 Getter 함수를 호출함 동작함
	 		*  2. -> User 오브젝트의 images 필드를 호출하기 위해 getImages()가 동작함
	 		*  3. 그 동작 결과로 Image 오브젝트의 getter 함수가 호출되고
	 		*  4. Image 오브젝트의 user 필드를 호출하기 위해 getUser()가 동작.
	 		*  5. 결국 무한 참조 발생
	 		*  따라서 4번에서 getUser가 이뤄지지 않도록 막아야 함.
	 		*  
			*/
		 
		
		

	}
	
	
}
