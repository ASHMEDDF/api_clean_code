package com.demo.api.clean.code.config;

import com.demo.api.clean.code.useCase.ProductsSum;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApplicationConfig {

    @Bean
    public ProductsSum productsSuma(){ return new ProductsSum();}
}
