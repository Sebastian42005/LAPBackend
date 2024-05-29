package org.example.buchibackend.security;

import lombok.RequiredArgsConstructor;
import org.example.buchibackend.domain.User;
import org.example.buchibackend.enums.Role;
import org.example.buchibackend.repository.UserRepository;
import org.example.buchibackend.security.jwt.JwtRequestFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.time.Instant;
import java.util.Date;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final UserRepository userRepository;
    private final CustomAuthenticationEntryPoint authenticationEntryPoint;
    private final JwtRequestFilter jwtRequestFilter;

    @Bean
    public User setUpAdminUser() {
        User admin = userRepository.findByEmail("admin@gmail.com").orElseGet(() ->
                User.builder()
                        .role(Role.ADMIN)
                        .email("admin@gmail.com")
                        .firstName("Admin")
                        .lastName("Administrator")
                        .birthdate(Date.from(Instant.now()))
                        .password(ShaUtils.decode("123456789"))
                        .build());
        return userRepository.save(admin);
    }

    @Bean
    public InMemoryUserDetailsManager userDetailsManager() {
        return new InMemoryUserDetailsManager();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .cors(AbstractHttpConfigurer::disable)
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> {
                    auth.requestMatchers("/**").permitAll();
                    auth.anyRequest().authenticated();
                })
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .httpBasic(http -> http.authenticationEntryPoint(authenticationEntryPoint))
                .httpBasic(Customizer.withDefaults())
                .addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class)
                .headers(httpSecurityHeadersConfigurer ->
                        httpSecurityHeadersConfigurer.contentSecurityPolicy(contentSecurityPolicyConfig ->
                                contentSecurityPolicyConfig.policyDirectives("frame-ancestors 'self' http://localhost:4200")))
                .build();
    }

}