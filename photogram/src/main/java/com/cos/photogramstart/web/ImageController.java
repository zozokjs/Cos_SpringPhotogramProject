package com.cos.photogramstart.web;

import java.util.List;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.cos.photogramstart.config.auth.PrincipalDetails;
import com.cos.photogramstart.domain.image.Image;
import com.cos.photogramstart.handler.ex.CustomValidationException;
import com.cos.photogramstart.service.ImageService;
import com.cos.photogramstart.web.dto.image.ImageUploadDto;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller 
public class ImageController {
	
	private final ImageService imageService;
	
	@GetMapping({"/", "/image/story"})
	public String story() {
		return "image/story";
	}
	
	
	//인기 페이지로 이동
	@GetMapping("/image/popular")
	public String popular(Model model) {
		
		// API는 데이터를 리턴함
		// 이건 아님.. AJAX 안 씀..
		List<Image> images = imageService.인기사진();
		
		model.addAttribute("images",images);
		
		return "image/popular";
	}
	
	
	@GetMapping("/image/upload")
	public String upload() {
		return "image/upload";
	}
	
	
	//이미지 업로드... 이미지, 텍스트가 같이 올라감
	@PostMapping("/image")
	public String imageUpload
	(ImageUploadDto imageUploadDto, @AuthenticationPrincipal PrincipalDetails principalDetails ) {
	
		//이미지 validation 체크
		if(imageUploadDto.getFile().isEmpty()) {
			System.out.println("이미지가 첨부되지 않았습니다.");
			throw new CustomValidationException("이미지가 첨부되지 않았습니다", null);
		}else {
			imageService.사진업로드(imageUploadDto, principalDetails);
		}
		
		return "redirect:/user/"+principalDetails.getUser().getId();
	}
	
}

