package com.cos.photogramstart.config.oauth;

import java.util.Map;
import java.util.UUID;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import com.cos.photogramstart.config.auth.PrincipalDetails;
import com.cos.photogramstart.domain.user.User;
import com.cos.photogramstart.domain.user.UserRepository;
import com.nimbusds.openid.connect.sdk.claims.UserInfo;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class OAuth2DetailsService extends DefaultOAuth2UserService{

	private final UserRepository userRepository;
	
	/**
	* 이렇게 주입하면 cycle 오류로 시작 불가. 
	* OAuth2DetailsService보다 securityConfig가 늦게 실행 되었다
	* 주입 -> OAuth2DetailsService -> BCryptPasswordEncoder 빈 등록 -> SecurityConfig [오류]
	* BCryptPasswordEncoder 빈 등록 -> SecurityConfig -> 주입 -> OAuth2DetailsService [이렇게 실행 되어야 오류 안 날듯..]
	private final BCryptPasswordEncoder bCryptPasswordEncoder;
	*/
	
	@Override
	public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {

		System.out.println("OAuth 서비스 탔음");
		
		OAuth2User oAuth2User = super.loadUser(userRequest);
		//System.out.println(oAuth2User.getAttributes()); //페이스북에서 되돌려준 회원 정보 확인
		
		Map<String, Object> userInformation = oAuth2User.getAttributes();
		
		String username = "facebook_"+(String)userInformation.get("id");
		String password = new BCryptPasswordEncoder().encode(UUID.randomUUID().toString());
		String name = (String)userInformation.get("name");
		String email = (String)userInformation.get("email");
		
		User userEntity = userRepository.findByUsername(username);
		
		//최초 로그인
		if(userEntity == null) {
			
			User user = User.builder()
					.username(username)
					.password(password)
					.name(name)
					.email(email)
					.role("ROLE_USER")
					.build();
			
			return new PrincipalDetails(userRepository.save(user), oAuth2User.getAttributes()) ;
		}else {
			//페이스북으로 이미 가입이 되어 잇음.
			return new PrincipalDetails(userEntity, oAuth2User.getAttributes());
		}
		
	}
	
	
}
