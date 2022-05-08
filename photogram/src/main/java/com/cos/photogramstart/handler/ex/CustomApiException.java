package com.cos.photogramstart.handler.ex;

import java.util.Map;


//RunTime시 발생되는 모든 Exception을 가로채려면 RuntimeException만 상속 받으면 됨
public class CustomApiException extends RuntimeException{

	/**
	 * 이 시리얼 번호는 JVM이 객체를 구분할 때 쓰인다고 함..
	 */
	private static final long serialVersionUID = 1L;

	public CustomApiException(String message) {
		super(message); 
	}
}
