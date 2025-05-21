package com.example.demo.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig implements WebMvcConfigurer {
	//	@Override
	//	public void addCorsMappings(CorsRegistry registry) {
	//		registry.addMapping("/**").allowedOrigins("http://localhost:5173") // ← 你的 React 開發伺服器 port
	//				.allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS").allowedHeaders("*").allowCredentials(true); // ⭐️ 讓 Cookie 與 Session 被傳遞
	//	}
}
