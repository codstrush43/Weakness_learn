package com.helper.configuration;

import com.helper.util.JWTUtil;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JwtUtilConfiguration {

    @Bean
    public JWTUtil jwtUtil() {
        return new JWTUtil();
    }

}
