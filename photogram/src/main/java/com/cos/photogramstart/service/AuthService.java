package com.cos.photogramstart.service;



import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cos.photogramstart.domain.user.User;
import com.cos.photogramstart.domain.user.UserRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service // ioc 등록하고 트랜젝션 관리하게 명시함
public class AuthService {

	
	private final UserRepository userRepository;
	private final BCryptPasswordEncoder bCryptPasswordEncoder;
	
	@Transactional
	//매개변수의 user는 클라이언트가 통신을 통해 전달한 회원가입 정보
	public User 회원가입(User user) {
		
		// 비번 가져와서 암호화
		String rawPassword=  user.getPassword();
		String encPassword = bCryptPasswordEncoder.encode(rawPassword);	
		user.setPassword(encPassword);

		user.setRole("ROLE_USER");
		
		//여기 있는 userEntity는 db에 있는 user 정보
		User userEntity = userRepository.save(user);
		
		return userEntity;
		
		
	}
	
	
}
