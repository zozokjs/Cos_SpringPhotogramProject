package com.cos.photogramstart.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cos.photogramstart.domain.comment.Comment;
import com.cos.photogramstart.domain.comment.CommentRepository;
import com.cos.photogramstart.domain.image.Image;
import com.cos.photogramstart.domain.user.User;
import com.cos.photogramstart.domain.user.UserRepository;
import com.cos.photogramstart.handler.ex.CustomApiException;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class CommentService {

	private final CommentRepository commentRepository;
	
	private final UserRepository userRepository;
	
	
	@Transactional
	public Comment 댓글쓰기(String content, int imageId, int userId) {
		
		
		Comment comment = new Comment();
		comment.setContent(content); // 댓글 내용
		
		// TIP
		//객체 만들 때 id 값만 담아서 insert 가능함.. 77번 영상 14분
		//DB의 comment 테이블에는 userId, imageId, content만 들어가면 됨
		//그러나 댓글을 등록하는 user의 username도 필요함. 
		/**
		 * 대신 return 시에 image 객체와 user 객체는 id 값만 갖고 있는 빈 객체를 리턴 받음
		 * */
		Image image = new Image();
		image.setId(imageId); //댓글이 등록된 이미지 아이디
		
		User userEntity = userRepository.findById(userId).orElseThrow(()->{
			throw new CustomApiException("유저 아이디를 찾을 수 없습니다. 관리자에게 문의하셈");
		});
		
		comment.setImage(image);
		comment.setUser(userEntity);
		
		return commentRepository.save(comment);

	}
	
	@Transactional
	public void 댓글삭제(int id) {
		
		try {
			commentRepository.deleteById(id);
		} catch (Exception e) {
			throw new CustomApiException(e.getMessage());
		}
	}
}
