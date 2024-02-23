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
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public UserDetailsService userDetailService() {
        return new UserDetailService();
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userDetailService());
        authenticationProvider.setPasswordEncoder(passwordEncoder());
        return authenticationProvider;
    }


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.cors(cors -> cors.disable())
                .authenticationProvider(authenticationProvider())
                .authorizeHttpRequests(request ->
                        request.requestMatchers("/getAll","/private-page").hasAnyAuthority("ADMIN", "CREATOR")
                                .requestMatchers("/normal-page").hasAuthority("NORMAL")
                                .requestMatchers("/public/public-page1", "/public/public-page2", "/register-process", "/register-page")
                                .permitAll()
                                .anyRequest()
                                .authenticated())
                .exceptionHandling(ex->ex.accessDeniedPage("/access-denied-page"))
                .formLogin(form -> form.loginPage("/login")
                        .loginProcessingUrl("/login-process")
                        .defaultSuccessUrl("/dashboard-page", true).permitAll())
                .logout(
                        logout -> logout
                                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                                .permitAll()
                );
        return httpSecurity.build();
    }


//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
//        httpSecurity.cors().and().csrf().disable()
//
//                .authorizeHttpRequests()
//                .antMatchers("/admin/**")
//                .hasAuthority("ADMIN")
//                .antMatchers("/**","/register-page/**","/register-process")
//                .permitAll()
//                .anyRequest()
//                .authenticated()
//                .and()
//                .formLogin()
//                .loginPage("/login")
//                .loginProcessingUrl("/login-process")
//                .defaultSuccessUrl("/register-page");
//        httpSecurity.authenticationProvider(authenticationProvider());
//        return httpSecurity.build();
//
//    }


}
