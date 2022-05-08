package com.cos.photogramstart.web.dto.image;

import org.springframework.web.multipart.MultipartFile;

import com.cos.photogramstart.domain.image.Image;
import com.cos.photogramstart.domain.user.User;

import lombok.Data;

//이미지 업로드를 위한 dto
@Data 
public class ImageUploadDto {
	
	private MultipartFile file;
	
	private String caption;
	
	
	public Image toEntity(String postImageUri, User user) {
		return Image.builder()
				.caption(caption)
				.postImageUrl(postImageUri)
				.user(user)
				.build();
	}
	
	
}
