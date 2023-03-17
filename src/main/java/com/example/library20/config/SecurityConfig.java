package com.example.library20.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    @Bean
    public CustomAuthenticationSuccessHandler customAuthenticationSuccessHandler(){
        return new CustomAuthenticationSuccessHandler();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
                .csrf().disable()
                .authorizeHttpRequests()
                .requestMatchers("/auth/**").permitAll()
                .requestMatchers("/books/public/**").authenticated()
                .requestMatchers("/authors/**").hasAuthority("ADMIN")
                .requestMatchers("/books/**").hasAuthority("ADMIN")
                .requestMatchers("/librarians/**").hasAuthority("ADMIN")
                .requestMatchers("/publishers/**").hasAuthority("ADMIN")
                .requestMatchers("/orders/edit/**").hasAuthority("LIBRARIAN")
                .anyRequest().authenticated();
        http
                .formLogin()
                .loginPage("/login")
                .usernameParameter("login")
                .passwordParameter("password")
                .successHandler(customAuthenticationSuccessHandler())
                .permitAll();
        http
                .logout()
                .permitAll();

        return http.build();
    }
}
