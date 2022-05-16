package com.cos.photogramstart.config.auth;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

import com.cos.photogramstart.domain.user.User;

import lombok.Data;

//일반적인 로그인, OAuth2를 이용한 로그인 시 
//모두 PrincipalDetails 타입으로 받기 위해 2개를 implements 받음.
@Data
public class PrincipalDetails implements UserDetails, OAuth2User{

	private static final long	 serialVersionUID = 1L;
	
	private User user;
	
	private Map<String, Object> attributes;
	
	public PrincipalDetails(User u) {
		this.user = u;
	}
	
	public PrincipalDetails(User u, Map<String, Object> attributes) {
		this.user = u;
	}
	
	
	//권한은 2개 이상일 수 있으므로 Collection 타입이다.
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {

		/**
		문법을 모르겠다면 이 메소드의 리턴 타입부터 명시한다.
		Collection<> collector;
		근데 GrantedAuthority 타입만 들어가도록 제네릭 되어 잇으므로
		Collection<GrantedAuthority> collector; 이렇게 만듬.
		*/
		
		Collection<GrantedAuthority> collector = new ArrayList<>();
		/**
		collector.add(new GrantedAuthority() {
			@Override
			public String getAuthority() {
				return user.getRole();
			}
		});
		*/
		//람다식
		/**
		 * return 되어야 하는건 User 객체가 가지고 있는 권한 목록임.
		 * 권한 목록은 Collection 타입으로 받아야 하고. 그런 다음 add를 해줘야 함.
		 * 달리 말해, add() 함수의 매개변수로는 함수가 들어가야 한다.
		 * getAuthority()를 통해야만 권한 목록을 가져 올 수 있고 또한,
		 * user.getRole()을 통해야만 권한 목록을 가져 올 수 있으므로.
		 * */
		collector.add(() -> {
			return user.getRole();			
		});
		
		return collector;
	}

	@Override
	public String getPassword() {
		return user.getPassword();
	}

	@Override
	public String getUsername() {
		return user.getUsername();
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}


	@Override
	public Map<String, Object> getAttributes() {
		return attributes; //{id=73dqwd.., name=dqwda, email=asdqwd@naver.com}가 리턴됨
	}


	@Override
	public String getName() {
		return (String)attributes.get("name");
	}

}
