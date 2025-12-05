package com.koreait.www.config;

import javax.servlet.Filter;
import javax.servlet.ServletRegistration.Dynamic;

import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

public class WebConfig extends AbstractAnnotationConfigDispatcherServletInitializer {

	@Override
	protected Class<?>[] getRootConfigClasses() {
		// TODO Auto-generated method stub
		return new Class [] {RootConfig.class};
	}

	@Override
	protected Class<?>[] getServletConfigClasses() {
		// TODO Auto-generated method stub
		return new Class [] { ServletConfiguration.class };
	}

	@Override
	protected String[] getServletMappings() {
		// TODO Auto-generated method stub
		return new String [] {"/"};
	}

	// filter 추가
	@Override
	protected Filter[] getServletFilters() {
		// filter encoding 추가
		CharacterEncodingFilter encoding = new CharacterEncodingFilter();
		
		// 들어오는 객체 인코딩 설정
		encoding.setEncoding("UTF-8");
		// 밖으로 나가는 데이터 인코딩 설정
		encoding.setForceEncoding(true);
		
		return new Filter [] {};
	}

	// 기타 사용자 설정 => customizeRegistration
	@Override
	protected void customizeRegistration(Dynamic registration) {
		// 파일 업로드 경로설정, 익셉션 처리 설정
		
	}

	
	
}
