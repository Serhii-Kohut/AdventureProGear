package com.example.adventureprogearjava.config;

import com.example.adventureprogearjava.exceptions.CustomAccessDeniedHandler;
import com.example.adventureprogearjava.filter.JwtAuthenticationFilter;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.servlet.util.matcher.MvcRequestMatcher;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.servlet.handler.HandlerMappingIntrospector;

import java.util.List;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@EnableMethodSecurity()
public class SecurityConfig {
    JwtAuthenticationFilter jwtAuthenticationFilter;
    AuthenticationEntryPoint authenticationEntryPoint;
    CustomAccessDeniedHandler customAccessDeniedHandler;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http,
                                                   HandlerMappingIntrospector introspect) throws Exception {

        MvcRequestMatcher.Builder mvcMatcherBuilder = new MvcRequestMatcher.Builder(introspect);

        http
                .csrf(csrfConfigurer ->
                        csrfConfigurer.ignoringRequestMatchers(
                                        mvcMatcherBuilder.pattern("/**"))
                                .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse()))

                .headers(headersConfigurer ->
                        headersConfigurer.frameOptions(HeadersConfigurer.FrameOptionsConfig::sameOrigin))

                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))

                .authorizeHttpRequests(auth ->
                        auth
                                .requestMatchers("/swagger-ui/**", "/v3/api-docs/**").permitAll()
                                .requestMatchers(mvcMatcherBuilder.pattern(HttpMethod.GET, "/api/blog/reactions/{postId}/count")).permitAll()
                                .requestMatchers(mvcMatcherBuilder.pattern(HttpMethod.GET, "/api/blog/posts/**")).permitAll()
                                .requestMatchers(mvcMatcherBuilder.pattern(HttpMethod.POST, "/api/blog/posts/**")).hasAuthority("ROLE_ADMIN")
                                .requestMatchers(mvcMatcherBuilder.pattern(HttpMethod.GET, "/api/productAttributes/**")).permitAll()
                                .requestMatchers(mvcMatcherBuilder.pattern("/api/public/**")).permitAll()
                                .requestMatchers(mvcMatcherBuilder.pattern("/api/public/**")).permitAll()
                                .requestMatchers(mvcMatcherBuilder.pattern("/api/**")).authenticated()
                                .requestMatchers(mvcMatcherBuilder.pattern("/**")).permitAll()

                )
                .exceptionHandling(exceptionHandling ->
                        exceptionHandling

                                .authenticationEntryPoint(authenticationEntryPoint)
                                .accessDeniedHandler(customAccessDeniedHandler))

                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();

        configuration.setAllowedOrigins(List.of("*"));
        configuration.setAllowedMethods(List.of("*"));
        configuration.setAllowedHeaders(List.of("*"));
        configuration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);

        return source;
    }
}