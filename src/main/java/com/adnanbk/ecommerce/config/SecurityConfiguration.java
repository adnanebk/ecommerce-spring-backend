package com.adnanbk.ecommerce.config;

import com.adnanbk.ecommerce.jwt.JwtAuthorizationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true)
@RequiredArgsConstructor
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    private final JwtAuthorizationFilter jwtAuthorizationFilter;


    public @Bean PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    protected @Override
    void configure(HttpSecurity http) throws Exception {
        //must be removed in production
        http.headers().frameOptions().disable();
        http.cors().and()
                .exceptionHandling()
                .authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED))
                .and()
                .csrf().disable()
                .authorizeRequests()
                .antMatchers("/h2-console/**","/swagger*/**","/v2/api-docs/**",
                "/api/auth/login/**","/api/auth/register/**","/api/auth/refresh-token/**",
                "/uploads/**").permitAll()
                .antMatchers(HttpMethod.GET,"/api/products/**").permitAll()
                .antMatchers(HttpMethod.GET,"/api/categories/**").permitAll()
                .anyRequest().authenticated();
                http.addFilterBefore(jwtAuthorizationFilter, UsernamePasswordAuthenticationFilter.class)
                    .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    }



    @Bean
    AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        final var source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(List.of("*"));
        configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(List.of("authorization", "content-type", "x-auth-token", "refresh-token"));
        configuration.setExposedHeaders(List.of("x-auth-token"));
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    // add this to not trigger the filter more than once
    @Bean
    public FilterRegistrationBean<JwtAuthorizationFilter> registration(JwtAuthorizationFilter filter) {
        var registration = new FilterRegistrationBean<>(filter);
        registration.setEnabled(false);
        return registration;
    }

}


