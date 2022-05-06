package com.cos.photogramstart.handler;

import java.util.Map;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

import com.cos.photogramstart.handler.ex.CustomValidationException;

@RestController
@ControllerAdvice //모든 exception을 가로챈다.
public class ControllerExceptionHandler {

	//매개변수에 RuntimeException.class을 넣으면 runTime 시 발생되는 모든 exception을 가로챈다.
	@ExceptionHandler(RuntimeException.class)
	public Map validationException(CustomValidationException e) {
		return e.getErrorMap();
	}
	
}
