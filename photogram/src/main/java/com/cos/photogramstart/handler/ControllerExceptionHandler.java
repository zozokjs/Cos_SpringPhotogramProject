package com.cos.photogramstart.handler;

import java.util.Map;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

import com.cos.photogramstart.handler.ex.CustomValidationException;
import com.cos.photogramstart.utl.Script;
import com.cos.photogramstart.web.dto.CMRespDto;

@RestController
@ControllerAdvice //모든 exception을 가로챈다.
public class ControllerExceptionHandler {

	
	//에러 처리를 위한 별도의 DTO를 만들고, 그걸로 메세지와 상세 오류 정보를 리턴 받음.
	//매개변수에 RuntimeException.class을 넣으면 runTime 시 발생되는 모든 exception을 가로챈다.
	@ExceptionHandler(RuntimeException.class)
	//와일드카드 써서 그 어떤 데이터 타입이라도 return 받을 수 있도록 한다.
	/**
	public CMRespDto<?> validationException(CustomValidationException e) {
	return new CMRespDto<Map<String,String>>(-1, e.getMessage(), e.getErrorMap());
	}	
	
	위아래 방식 비교
	-> 클라이언트 응답할 땐 아래가 좋음
	-> ajax 통신할 때나 안드로이드 통신할 땐 는 위가 좋음.
	
	*/	
	public String validationException(CustomValidationException e) {
		
		
		return Script.back(e.getErrorMap().toString());
		
		
	}
	
}
 