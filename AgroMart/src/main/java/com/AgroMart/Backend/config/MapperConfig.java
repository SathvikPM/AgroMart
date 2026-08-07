package com.AgroMart.Backend.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MapperConfig {

    @Bean
    public ModelMapper modelMapper(){
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration()   //skips the nullable values(used for Partial update(patch))
                .setSkipNullEnabled(true);
        return  modelMapper;
    }
}
