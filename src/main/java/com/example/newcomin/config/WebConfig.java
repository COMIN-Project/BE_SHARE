package com.example.newcomin.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

// CORS 관련 설정(포트 문제 해결을 위해서)
@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {

        // 모든 경로에 대해 CORS 설정 추가
        registry.addMapping("/**")

                // 포트 8080 3000 허용
                .allowedOrigins("http://13.125.190.19:8080", "http://13.125.190.19:3000",
                        "http://localhost:3000", "http://localhost:3000")

                // GET, POST, PUT, DELETE 메서드 허용
                .allowedMethods("GET", "POST", "PUT", "DELETE")

                // 모든 요청 헤더 허용
                .allowedHeaders("*")

                // 인증 정보(쿠키)를 요청에 포함
                .allowCredentials(true)

                // 3600초(1시간)마다 사전 요청의 결과를 캐시
                .maxAge(3600);
    }
}