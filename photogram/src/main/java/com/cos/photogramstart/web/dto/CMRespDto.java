package com.cos.photogramstart.web.dto;

import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
//에러 메세지를 응답 받을 때 쓰는 DTO
public class CMRespDto<T> {

	private int code; // 1은 성공, -1은 실패 처리로 커스텀할꺼임
	private String message;	
	private T data;

}
