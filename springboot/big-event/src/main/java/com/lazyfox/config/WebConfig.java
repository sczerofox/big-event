package com.lazyfox.config;

import com.lazyfox.interceptors.LoginInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @ClassName WebConfig
 * @Description
 * @Author lazyFox
 * @Date 2024/7/27 9:49
 * @Version V0.1
 */

@Configuration
public class WebConfig implements WebMvcConfigurer {

	@Autowired
	private LoginInterceptor loginInterceptor;

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		//  登录和注册接口不拦截
		registry.addInterceptor(loginInterceptor)
				.excludePathPatterns("/user/login","/user/register");
	}
}

