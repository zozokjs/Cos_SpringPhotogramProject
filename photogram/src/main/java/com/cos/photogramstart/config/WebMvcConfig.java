package com.cos.photogramstart.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.resource.PathResourceResolver;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer{
	//WebMvcConfigurer을 상속 받았으므로 web 설정 파일 역할을 할 수 있다.
	
	@Value("${file.path}")
	private String uploadFolder;
	
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		WebMvcConfigurer.super.addResourceHandlers(registry);
		
		
		registry
		.addResourceHandler("/upload/**") 
		//JSP 페이지에서 /upload/~~이런 패턴이 나오면 발동됨.
		// ex)profil.jsp에서 image부분 
		.addResourceLocations("file:///"+uploadFolder)
		.setCachePeriod(60*10*6) //60초 x 10 x 6 = 1시간 동안 이미지 캐싱
		.resourceChain(true)
		.addResolver(new PathResourceResolver());
	}
	
	
}
