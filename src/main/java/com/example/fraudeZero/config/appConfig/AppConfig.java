package com.example.fraudeZero.config.appConfig;

import com.example.fraudeZero.service.BrasilApiService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.web.client.RestTemplate;

@Configuration
public class AppConfig {

    @Bean
    public RestTemplate restTemplate(){
        return new RestTemplate();
    }

    @Bean
    public LocalValidatorFactoryBean validator(BrasilApiService brasilApiService){
        LocalValidatorFactoryBean bean = new LocalValidatorFactoryBean();
        bean.getValidationPropertyMap().put("javax.persistence.validation.mode", "none");
        return bean;
    }
}
