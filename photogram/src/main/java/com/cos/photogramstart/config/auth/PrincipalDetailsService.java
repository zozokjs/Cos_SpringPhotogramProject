package com.cos.photogramstart.config.auth;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.cos.photogramstart.domain.user.User;
import com.cos.photogramstart.domain.user.UserRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class PrincipalDetailsService implements UserDetailsService{

	
	private final UserRepository userRepository;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
	//이 메소드릐 리턴이 잘 되면 자동으로 PrincipalDetails 타입을 시큐리티 세션으로 만든다.
		//password는 시큐리티가 알아서 확인하니까 신경 안써도 됨.
		
		//System.out.println("이거 실행 되나???");
		
		User userEntity =  userRepository.findByUsername(username);
		

		if(userEntity == null) { //유저 없음
			return null;			
		}else { //유저 찾았음
			return new PrincipalDetails(userEntity);
		}
		
	}

}
