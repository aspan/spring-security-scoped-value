package com.github.spring.security.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;

@Configuration
@ConditionalOnProperty(name = "spring.security.scoped.mode", havingValue = "security-filter")
public class WebSecurityConfig {
    @Bean
    Customizer<HttpSecurity> httpSecurityCustomizer() {
        return http -> http.with(new ScopedSecurityContextConfigurer<>(), Customizer.withDefaults());
    }
}