package com.cos.photogramstart.handler.ex;

import java.util.Map;


//RunTime시 발생되는 모든 Exception을 가로채려면 RuntimeException만 상속 받으면 됨
public class CustomValidationException extends RuntimeException{

	/**
	 * 이 시리얼 번호는 JVM이 객체를 구분할 때 쓰인다고 함..
	 */
	private static final long serialVersionUID = 1L;

	//private String message;
	private Map<String, String> errorMap;
	
	public CustomValidationException(String message, Map<String, String> errorMap) {
		super(message); //부모한테 던짐. 부모가 message를 return하고 있으므로 Getter()가 별도로 필요 없음. 
		//Throwable 클래스의 getMessage()
		//this.message = message;
		this.errorMap = errorMap;
	}
		
	public Map<String, String>getErrorMap(){
		return errorMap;
	}
	
}
