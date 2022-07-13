package com.cos.photogramstart.service;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
	
	
	
	@Transactional(readOnly = true)
	public List<Image> 인기사진(){
		
		return imageRepository.mPopular();
	}
	
	
	
	
	
	
	//구독한 사람의 이미지 가져오기?
	@Transactional(readOnly = true)
	public Page<Image> 이미지스토리(int principalId, Pageable pageable){
		
		Page<Image> images = imageRepository.mStory(principalId, pageable);
		
		//images에 좋아요 여부를 담아야 함
		images.forEach((image)->{
			
			image.setLikeCount(image.getLikes().size());	

			//어떤 이미지에 좋아요한 사람을 모두 가져와서
			image.getLikes().forEach((like)->{
				
				//그 좋아요를 로그인한 사람이 했는지를 확인
				if(like.getUser().getId() == principalId) {
					//좋아요 했음.
					image.setLikeState(true);
				
				}
				else {
					 //좋아요 안 했음
				}
			});
		});
		
		
		return images;
	}
	
	
	
	//사진 업로드가 프로필사진과 일반사진 업로드 2종류 있음
	@Transactional
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
		
		
		//System.out.println(imageEntity);
		/**
		 * (위 메소드의 실행 결과는 imageEntity.toString()과 동일함).
		 * imageEntity를 기본적으로 생성하고 @Data를 붙이면 어노테이션 덕분에
		 * toString() 메소드가 자동으로 생성 된다.
		 * 그런데, Image 클래스에는 User 오브젝트가 있따. 
		 * User 오브젝트.toString()을 출력할 것인데
		 * User 오브젝트에는 List<Image>image가 있다.
		 * 다시 image.toString()을 할 것이므로
		 * 결과적으로 무한참조가 발생되어 오류가 생김.
		 * 
		 * 따라서, Image 클래스에서 toString()을 재정의하면서 User 오브젝트를 제외하면
		 * 무한참조 오류를 피할 수 있다.
		 * */
		
	}
	
	
	
}
