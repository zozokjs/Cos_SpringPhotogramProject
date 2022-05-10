package com.cos.photogramstart.service;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.cos.photogramstart.domain.subscribe.SubscribeRepository;
import com.cos.photogramstart.domain.user.User;
import com.cos.photogramstart.domain.user.UserRepository;
import com.cos.photogramstart.handler.ex.CustomApiException;
import com.cos.photogramstart.handler.ex.CustomException;
import com.cos.photogramstart.handler.ex.CustomValidationApiException;
import com.cos.photogramstart.web.dto.user.UserProfileDto;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class UserService {

	private final UserRepository userRepository;

	private final BCryptPasswordEncoder bCryptPasswordEncoder;

	private final SubscribeRepository subscribeRepository;

	//application.yml 파일에 명시한 file.path를 그대로 가져옴
	@Value("${file.path}")
	private String uploadFolder ;
		
		
	@Transactional
	public User 회원프로필사진변경(int principalId, MultipartFile profileImageFile) {

		// 유일성이 보장되는 id를 만들어줌
		UUID uuid = UUID.randomUUID();

		// 실제 파일 이름
		// ex) 06fcd823-67fd-44f2-855a-be9c823d995e_1 (1).jpg
		String imageFileName = uuid + "_" + profileImageFile.getOriginalFilename();

		System.out.println("이미지 이름 > " + imageFileName);

		Path imageFilePath = Paths.get(uploadFolder + imageFileName);

		try {
			// 파일 작성... i/o 과정에서 예외 발생 가능.
			Files.write(imageFilePath, profileImageFile.getBytes());
		} catch (Exception e) {
			e.printStackTrace();
		}

		User userEntity = userRepository.findById(principalId).orElseThrow(()->{
			throw new CustomApiException("유저를 찾을 수 없습니다.");
		});
		
		userEntity.setProfileImageUrl(imageFileName);
		
		return userEntity;
	} // 더티 채킹으로 업데이트 된다. 
	
	

	// 로그인한 사용자의 프로필이 아닌,
	// 사용자가 보길 원하는 회원의 프로필이 표시되어야 함. 그래서 id를 받아야 함.
	/**
	 * user 정보, user가 올린 image 정보, 로그인한 사용자가 user를 구독중인지에 대한 정보 , user가 작성한 게시글 수 등이
	 * 필요함.
	 */
	@Transactional(readOnly = true)
	// 서비스 단에는 select만 하더라도 Transactional을 걸어주는 게 좋다.
	// readonly True를 넣으면 읽기전용으로 인식하므로 jpa는
	// 영속성 컨텍스트 내의 변경 여부를 감시 및 감지 하지 않는다.
	public UserProfileDto 회원프로필(int pageUserId, int principalId) {

		// SELECT * FROM image WHERE userid = :userid;

		UserProfileDto dto = new UserProfileDto();

		// id로 이미지 못 찾을 수도 있으니까 orElseThrow
		User userEntity = userRepository.findById(pageUserId).orElseThrow(() -> {
			throw new CustomException("해당 프로필 페이지는 없는 페이지 입니다.");
		});

		// 사용자 정보 페이지의 사용자 아이디와 로그인한 아이디가 같으면 true
		dto.setPageOwnerState(pageUserId == principalId);
		dto.setUser(userEntity);
		dto.setImageCount(userEntity.getImages().size());

		// 구독 상태
		int subscribeState = subscribeRepository.mSubscribeState(principalId, pageUserId);
		// 구독 수
		int subscribeCount = subscribeRepository.mSubscribeCount(pageUserId);

		dto.setSubscribeCount(subscribeCount);
		dto.setSubscribeState(subscribeState == 1);

		// 이미지마다 존재하는 좋아요 갯수
		userEntity.getImages().forEach((image) -> {
			image.setLikeCount(image.getLikes().size());
		});

		return dto; // 조회된 회원 프로필 정보
	}

	@Transactional
	public User 회원수정(int id, User user) {
		// 영속화를 하고 > 영속화된 오브젝트를 수정하면 > 더티 체킹이 되면서 자동으로 db에 반영 됨

		// 1. 영속화
		/**
		 * Optional..을 쓰면 findById()의 결과는 아래처럼 3가지로 나뉨. 무조건 찾았음(get) / 못찾았다면 exception
		 * 발동(orElseThrow) / orElse => id를 찾았다면 get() 함수까지 받을 필요 없으나. 못 찾았을 경우를 대비해서
		 * get() 함수까지 써야 한다.? 35번 영상
		 */
		// 즉, userEntity는 영속화된 데이터임.
		/**
		 * User userEntity = userRepository.findById(10).orElseThrow(new
		 * Supplier<IllegalArgumentException>() {
		 * 
		 * @Override public IllegalArgumentException get() { return new
		 *           IllegalArgumentException("찾을 수 없는 id 입니다."); } });
		 */
		User userEntity = userRepository.findById(id).orElseThrow(() -> {
			return new CustomValidationApiException("찾을 수 없는 id 입니다.");
		});

		// 암호화부터
		String rawPassword = user.getPassword();
		String encPassword = bCryptPasswordEncoder.encode(rawPassword);

		userEntity.setName(user.getName());
		userEntity.setPassword(encPassword);
		userEntity.setBio(user.getBio());
		userEntity.setWebsite(user.getWebsite());
		userEntity.setPhone(user.getPhone());
		userEntity.setGender(user.getGender());

		return userEntity;
		// return 이후 더치 체킹 발생하여 업데이트 됨
	}

}
