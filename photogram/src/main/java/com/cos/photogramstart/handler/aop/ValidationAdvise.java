package com.cos.photogramstart.handler.aop;

import java.util.HashMap;
import java.util.Map;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import com.cos.photogramstart.handler.ex.CustomValidationApiException;
import com.cos.photogramstart.handler.ex.CustomValidationException;

@Component // 레스트 컨트롤, 서비스, 컨트롤러 등이 모두 이 어노테이션을 상속해서 만들어진 것임
@Aspect
public class ValidationAdvise {

	/**
	 * Arround 메소드 실행 내내 검사함 첫번째 * -> public, protected, private 관계 없이
	 * com.cos.web.api.*Controller.*(..) -> ~Controller로 끝나는 모든 클래스에서, 모든 메소드를, 파라미터
	 * 관계 없이.
	 */
	@Around("execution(* com.cos.photogramstart.web.api.*Controller.*(..))")
	public Object apiAdvise(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
		// proceedingJoinPoint는 위에서 명시한 범위 내의 매개변수 등에 접근할 수 있음
		// return proceedingJoinPoint.proceed();는 모두 접근한 다음에 그 함수로 돌아가라는 명령이다.
		/**
		 * 예를 들어, profile() 함수보다 먼저 이 메서드가 실행 되며 return proceedingJoinPoint.proceed();하는
		 * 순간 profile() 함수가 실행되기 시작함.
		 * 
		 * 
		 */
		System.out.println("web api 컨트롤러========================================");

		Object[] args = proceedingJoinPoint.getArgs();
		for (Object arg : args) {
			// System.out.println(arg);

			// org.springframework.validation.BeanPropertyBindingResult:
			/**
			 * 위와 같은 타입이면 아래처럼 낚아 챌 수 있다.
			 */
			if (arg instanceof BindingResult) {
				System.out.println("유효성을 검사하는 함수입니다.");

				BindingResult bindingResult = (BindingResult) arg;

				if (bindingResult.hasErrors()) {
					Map<String, String> errorMap = new HashMap<>();
					for (FieldError error : bindingResult.getFieldErrors()) {

						errorMap.put(error.getField(), error.getDefaultMessage());
						/*
							 System.out.println("======================");				 
							 System.out.println(error.getDefaultMessage());  
							 System.out.println("======================");		
						*/
					}
					throw new CustomValidationApiException("유효성 검사 실패함", errorMap);
				}

			}

		}

		return proceedingJoinPoint.proceed();

	}

	@Around("execution(* com.cos.photogramstart.web.*Controller.*(..))")
	public Object advise(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {

		System.out.println("web 컨트롤러========================================");
		Object[] args = proceedingJoinPoint.getArgs();

		for (Object arg : args) {
			// org.springframework.validation.BeanPropertyBindingResult:
			if (arg instanceof BindingResult) {
				
				System.out.println("유효성을 검사하는 함수입니다.");
				BindingResult bindingResult = (BindingResult) arg;
				
				if (bindingResult.hasErrors()) {
					Map<String, String> errorMap = new HashMap<>();
					for (FieldError error : bindingResult.getFieldErrors()) {

						errorMap.put(error.getField(), error.getDefaultMessage());

						//System.out.println("======================");
						//System.out.println(error.getDefaultMessage());
						// 라이브러리가 알아서 파악해서 한글화해서 메세지 뱉어줌
						// ex) 20 이하여야 합니다 / 공백일 수 없습니다
						//System.out.println("======================");
					}
					throw new CustomValidationException("유효성 검사 실패함", errorMap);
					// Exception 발동시킴...
					// ControllerExceptionHandler에서 모든 Exception을 가로채게 했으므로 그 쪽 클래스가 발동함.

				}
			}
		}

		return proceedingJoinPoint.proceed();
	}

}
