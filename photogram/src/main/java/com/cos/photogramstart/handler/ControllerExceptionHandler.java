package com.cos.photogramstart.handler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

import com.cos.photogramstart.handler.ex.CustomValidationApiException;
import com.cos.photogramstart.handler.ex.CustomValidationException;
import com.cos.photogramstart.utl.Script;
import com.cos.photogramstart.web.dto.CMRespDto;

@RestController
@ControllerAdvice // 모든 exception을 가로챈다.
public class ControllerExceptionHandler {

	// 에러 처리를 위한 별도의 DTO를 만들고, 그걸로 메세지와 상세 오류 정보를 리턴 받음.
	// 매개변수에 RuntimeException.class을 넣으면 runTime 시 발생되는 모든 exception을 가로챈다.
	@ExceptionHandler(RuntimeException.class)
	// 와일드카드 써서 그 어떤 데이터 타입이라도 return 받을 수 있도록 한다.
	public String validationException(CustomValidationException e) {

		return Script.back(e.getErrorMap().toString()); //자바스크립트 리턴

	}

	/**
	 * 클라이언트 응답할 땐 아래가 좋음 / ajax 통신할 때나 안드로이드 통신할 땐 는 위가 좋음.
	 */	
	
	//Ajax 요청 시 exception 발생하면 여기로 온다. 
	//ex) 회원정보 수정 완료 처리 시 
	@ExceptionHandler(CustomValidationApiException.class)
	public ResponseEntity<CMRespDto<?>> validationApiException(CustomValidationApiException e) {
	
		System.out.println("===================================== Exception 낚아챘나?");
		
		return new ResponseEntity<>(new CMRespDto<>(-1, e.getMessage(), e.getErrorMap()), HttpStatus.BAD_REQUEST); //데이터 리턴
	}

	
	
}
