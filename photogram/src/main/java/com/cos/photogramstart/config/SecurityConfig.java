package com.cos.photogramstart.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.cos.photogramstart.config.oauth.OAuth2DetailsService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@EnableWebSecurity //시큐리티 활성화 
@Configuration // ioc 등록
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	
	private final OAuth2DetailsService oAuth2DetailsService;
	
	@Bean
	public BCryptPasswordEncoder encode() {
		return new BCryptPasswordEncoder();
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
	
		//super.configure(http); //부모가 가진 시큐리티 기본 로그인 기능을 주석처리 함으로써 비활성화
		
		http.csrf().disable(); //CSRF 토큰을 비활성화 함
		/**
		 * 1. 클라이언트가 회원가입 창을 요청하면
		 * 2. 시큐리니틑는FORM에 CSRF 토큰을 심어서 클라이언트에게 가입창을 리턴하고
		 * 3. 클라이언트가 가입 창에 입력하고 가입을 요청하면
		 * 4. 시큐리티가 이전에 FORM에 심었던 토큰이 있는지, 그 토큰이 맞는지 검사함. 이때 쓰임.
		 * */
		
		http.authorizeRequests()
				.antMatchers("/", "/user/**", "/image/**", "/subscribe/**", "/comment/**", "/api/**").authenticated() // 이 주소는 인증 필요함
				.anyRequest().permitAll()  // 그 외의 요청은 허용함
				.and()
				.formLogin() 
				.loginPage("/auth/signin") //antMatcher()에 적힌 주소로 접근한다면 이 주소로 향해라  GET요청임
				.loginProcessingUrl("/auth/signin") //이 주소로 POST 방식 요청하면 시큐리티가 로그인을 낚아채서 진행해줌.
				.defaultSuccessUrl("/") //loginPage()에 적힌 주소에서 인증 되었다면 그 다음 이 주소로 향해라. 
				.and()
				.oauth2Login()  //oauth2 로그인도 할거임
				.userInfoEndpoint() //oauth2 로그인을 하면 최종 응답을 회원 정보로 바로 받을 수 있게 해줘
				.userService(oAuth2DetailsService); //
		
	}
	
	
	
}
