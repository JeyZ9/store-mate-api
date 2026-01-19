package com.sm.jeyz9.storemateapi.config;

import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableMethodSecurity
@SecurityScheme(
        name = "Bearer Authentication",
        type = SecuritySchemeType.HTTP,
        scheme = "bearer",
        bearerFormat = "JWT"
)
public class SecurityConfig {
    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.cors(Customizer.withDefaults()).csrf(csrf -> csrf.disable())
                .authorizeHttpRequests((authorize) -> 
                        authorize
                                .requestMatchers(HttpMethod.GET,
                                        "/v3/api-docs/**",
                                        "/swagger-ui.html",
                                        "/swagger-ui/**"
                                ).permitAll()
                                .anyRequest().authenticated()
                        )
//                TODO
//                .exceptionHandling(exception -> exception
//                        .authenticationEntryPoint()
//                        .authenticationEntryPoint()
//                        .accessDeniedHandler()
//                )
                        .sessionManagement(session -> session
                            .sessionCreationPolicy(SessionCreationPolicy.STATELESS
                        )
                );
//        TODO
//        http.addFilterBefore()
        return http.build();
    }
}
