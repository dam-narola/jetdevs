package com.narola.jetdevstest.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.authority.mapping.SimpleAuthorityMapper;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.servlet.util.matcher.MvcRequestMatcher;

@EnableWebSecurity
@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http,
                                                   UserService userService,
                                                   APIAuthenticationEntryPoint entryPoint) throws Exception {
        return http
                .csrf().disable()
                .cors().disable()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests().antMatchers("/swagger-ui.html","/v2/api-docs",
                        "/swagger-ui*","/springfox*","/webjars/**","/swagger-resources/**")
                .permitAll()
                .anyRequest()
                .authenticated()
                .and()
                .httpBasic()
                .and()
                .authenticationManager(authenticationManager(userService))
                .exceptionHandling()
                .authenticationEntryPoint(entryPoint)
                .and()
                .build();
    }

    @Bean
    public AuthenticationManager authenticationManager(UserService userService) {
        return authentication -> {
            String username = (String) authentication.getPrincipal();
            String password = (String) authentication.getCredentials();
            UserDetails userDetails = userService.loadUserByUsername(username);
            if (passwordEncoder().matches(password, userDetails.getPassword())) {
                UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(username, password, userDetails.getAuthorities());
                SecurityContextHolder.getContext().setAuthentication(token);
                return token;
            } else {
                throw new RuntimeException("Invalid username or password");
            }
        };
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SimpleAuthorityMapper authorityMapper() {
        SimpleAuthorityMapper authorityMapper = new SimpleAuthorityMapper();
        authorityMapper.setConvertToUpperCase(true); // Ensures role names are treated case-insensitively
        authorityMapper.setPrefix(""); // Removes the default "ROLE_" prefix
        return authorityMapper;
    }

}
