package com.cos.photogramstart.service;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cos.photogramstart.domain.user.User;
import com.cos.photogramstart.domain.user.UserRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class UserService {

	private final UserRepository userRepository;
	
	private final BCryptPasswordEncoder bCryptPasswordEncoder;
	
	@Transactional
	public User 회원수정(int id, User user) {
		//영속화를 하고 > 영속화된 오브젝트를 수정하면 > 더티 체킹이 되면서 자동으로 db에 반영 됨
		
		//1. 영속화
		/**Optional..을 쓰면 
		 * findById()의 결과는 아래처럼 3가지로 나뉨.
		 *무조건 찾았음(get) / 못찾았음 exception 발동(orElseThrow) / orElse
		  => id를 찾았다면 get() 함수까지 받을 필요 없으나.
		  못 찾았을 경우를 대비해서 get() 함수까지 써야 한다.? 35번 영상
		*/		
		//즉, userEntity는 영속화된 데이터임.
		User userEntity = userRepository.findById(id).get();

		//암호화부터
		String rawPassword = user.getPassword();
		String encPassword = bCryptPasswordEncoder.encode(rawPassword);
		
		userEntity.setName(user.getName());	
		userEntity.setPassword(encPassword);
		userEntity.setBio(user.getBio());
		userEntity.setWebsite(user.getWebsite());
		userEntity.setPhone(user.getPhone());
		userEntity.setGender(user.getGender());

		return userEntity;
		//return 이후 더치 체킹 발생하여 업데이트 됨
	}
	
}
