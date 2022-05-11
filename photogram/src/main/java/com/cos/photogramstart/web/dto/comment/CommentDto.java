package com.cos.photogramstart.web.dto.comment;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import lombok.Data;


//Not Null = Null 허용 불가
//NotEmpty = Null과 빈 값 허용 불가
//NotBlank = 빈 값과 null, " "공백문자열을 허용하지 않음
@Data
public class CommentDto {

	@NotBlank
	private String content;
	
	@NotNull
	private Integer imageId;
	
	//toEntity가 필요 없음...
	
}
