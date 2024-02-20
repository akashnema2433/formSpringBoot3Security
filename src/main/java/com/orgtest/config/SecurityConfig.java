package com.orgtest.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public UserDetailsService userDetailService(){
        return new UserDetailService();
    }

    @Bean
    public AuthenticationProvider daoAuthenticationProvider(){
        DaoAuthenticationProvider provider=new DaoAuthenticationProvider();
        provider.setPasswordEncoder(passwordEncoder());
        provider.setUserDetailsService(userDetailService());
        return provider;
    }


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.cors(cors->cors.disable())
                .authenticationProvider(daoAuthenticationProvider())
                .authorizeHttpRequests(request->
                        request.requestMatchers("/dashboard-page","/getAll").hasAnyAuthority("ADMIN","CREATOR")
                                .requestMatchers("/normal-page").hasAuthority("NORMAL")
                                .requestMatchers("/public-page1","/public-page2","/register-page/**","/register-process")
                                .permitAll()
                                .anyRequest()
                                .authenticated())
                .formLogin(form->form.loginPage("/login").permitAll()
                        .loginProcessingUrl("/login-process")
                        .defaultSuccessUrl("/register-page/**"));
        return httpSecurity.build();
    }

}
