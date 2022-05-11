package com.cos.photogramstart.domain.comment;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.cos.photogramstart.domain.image.Image;

public interface CommentRepository extends JpaRepository<Comment, Integer> {

	/**
	 * 
	 * 	 Native Query는 return 형태를 반드시 void / int / Integer 형태로만 받을 수 있음.
	 *  아래는 사용 못함.
	 * 
	@Modifying
	@Query(value = "INSERT INTO comment(content, imageId, userId, createDate) VALUES(:content, :imageId, :userId, now())", nativeQuery = true)
	Comment mSave(String content, int imageId, int userId);
	*/
}
