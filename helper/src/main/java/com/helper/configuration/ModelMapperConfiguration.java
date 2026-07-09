package com.helper.configuration;

import org.springframework.context.annotation.Configuration;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;

@Configuration
public class ModelMapperConfiguration {
    
    @Bean
    public  ModelMapper modelMapper() {
        return new ModelMapper();
    }

}
