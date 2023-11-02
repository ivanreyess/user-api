package com.sv.userapi.config;

import jakarta.validation.Validator;
import org.springframework.boot.autoconfigure.orm.jpa.HibernatePropertiesCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;

@Configuration
@Lazy
public class AppConfig {
        @Bean
        @Lazy
        public HibernatePropertiesCustomizer hibernatePropertiesCustomizer(final Validator validator) {
            return hibernateProperties -> hibernateProperties.put("javax.persistence.validation.factory", validator);
        }

}
