package com.cos.photogramstart.web.api;

import java.util.HashMap;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.cos.photogramstart.config.auth.PrincipalDetails;
import com.cos.photogramstart.domain.comment.Comment;
import com.cos.photogramstart.handler.ex.CustomValidationApiException;
import com.cos.photogramstart.service.CommentService;
import com.cos.photogramstart.web.dto.CMRespDto;
import com.cos.photogramstart.web.dto.comment.CommentDto;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
public class CommentApiController {

	private final CommentService commentService;

	// ajax를 통해 JSON으로 전달 된 데이터는 @RequestBody로 받아야 한다.
	@PostMapping("/api/comment")
	public ResponseEntity<?> commentSave(
			@Valid @RequestBody CommentDto commentDto, 
			BindingResult bindingResult,
			@AuthenticationPrincipal PrincipalDetails principalDetails) {

		// 댓글 작성 시 유효성 검사 실패 했을 때 
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
			// ControllerExceptionHandler에서 모든 Exception을 가로채게 했으므로 그 쪽 클래스가 발동함.
		}

		// System.out.println(commentDto);

		Comment comment = commentService.댓글쓰기(commentDto.getContent(), commentDto.getImageId(),
				principalDetails.getUser().getId());

		return new ResponseEntity<>(new CMRespDto<>(1, "댓글 쓰기 성공", comment), HttpStatus.CREATED);
	}

	@DeleteMapping("/api/comment/{id}")
	public ResponseEntity<?> commentDelete(@PathVariable int id) {

		commentService.댓글삭제(id);

		return new ResponseEntity<>(new CMRespDto<>(1, "댓글 삭제 성공", null), HttpStatus.OK);

	}

}
