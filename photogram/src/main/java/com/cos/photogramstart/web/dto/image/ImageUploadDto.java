package com.cos.photogramstart.web.dto.image;

import org.springframework.web.multipart.MultipartFile;

import lombok.Data;

//이미지 업로드를 위한 dto
@Data 
public class ImageUploadDto {
	
	private MultipartFile file;
	
	private String caption;
	
}
