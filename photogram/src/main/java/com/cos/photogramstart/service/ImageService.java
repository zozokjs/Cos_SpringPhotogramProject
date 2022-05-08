package com.cos.photogramstart.service;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.cos.photogramstart.config.auth.PrincipalDetails;
import com.cos.photogramstart.domain.image.Image;
import com.cos.photogramstart.domain.image.ImageRepository;
import com.cos.photogramstart.web.dto.image.ImageUploadDto;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class ImageService {

	//업로드.. 어어업로드...
	
	private final ImageRepository imageRepository;
	
	//application.yml 파일에 명시한 file.path를 그대로 가져옴
	@Value("${file.path}")
	private String uploadFolder ;
	
	
	public void 사진업로드(ImageUploadDto imageUploadDto, PrincipalDetails principalDetails) {
		
		//유일성이 보장되는 id를 만들어줌
		UUID uuid = UUID.randomUUID();
		
		//실제 파일 이름
		// ex) 06fcd823-67fd-44f2-855a-be9c823d995e_1 (1).jpg
		String imageFileName = uuid+"_"+imageUploadDto.getFile().getOriginalFilename();
		
		System.out.println("이미지 이름 > "+imageFileName);
		
		Path imageFilePath = Paths.get(uploadFolder+imageFileName );
		
		try {
			//파일 작성... i/o 과정에서 예외 발생 가능.
			Files.write(imageFilePath, imageUploadDto.getFile().getBytes());
			
		} catch (Exception e) {
			e.printStackTrace();
		
		}
		
		//imageUploadDto를 image 객체로 변환
		Image image = imageUploadDto.toEntity(imageFileName, principalDetails.getUser());
		
		Image imageEntity = imageRepository.save(image);
		
		System.out.println(imageEntity);
		
		
	}
	
	
	
}
