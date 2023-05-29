package com.example.capstone.pizza_delivery_service;

import javax.sql.DataSource;

import com.example.capstone.pizza_delivery_service.repositories.AuthGroupRepository;
import com.example.capstone.pizza_delivery_service.repositories.CustomersCredentialsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.authority.mapping.GrantedAuthoritiesMapper;
import org.springframework.security.core.authority.mapping.SimpleAuthorityMapper;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import org.thymeleaf.spring6.SpringTemplateEngine;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;


@Configuration
@EnableWebSecurity
public class WebSecurityConfig  {

    @Autowired
    private  CustomersCredentialsRepository customersCredentialsRepository;

    @Autowired
    private  AuthGroupRepository authGroupRepository;


    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(t->t
                .requestMatchers("/", "/index","/addtocart/{ID}","/login","/signup","/css/*", "/js/*","/images/*","/error","/pay","/signup","/error").permitAll()
                .requestMatchers("/orders","/customers","/customers/*").hasAuthority("ADMIN")
                .anyRequest().authenticated())
//                .and()
                .formLogin(form->form.loginPage("/login")
                        .loginProcessingUrl("/login")
                                .successHandler(successHandler()))
                .logout(out ->
                        out.logoutRequestMatcher(new
                                        AntPathRequestMatcher("/logout"))
                                .permitAll())
                .authenticationProvider(authProvider());
//                .sessionManagement(session -> session
//                        .sessionCreationPolicy(SessionCreationPolicy.ALWAYS)
//                        .invalidSessionUrl("/error.html")
//                        .maximumSessions(1)
//                        .maxSessionsPreventsLogin(true));;
        return http.build();

    }


    @Bean
    public UserDetailsService detailsService() {

        return new com.example.capstone.pizza_delivery_service.UserDetailsService(customersCredentialsRepository,authGroupRepository);
    }

    @Bean
    public AuthenticationProvider authProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setPasswordEncoder(encoder());
        provider.setUserDetailsService(detailsService());

        return provider;
    }

    @Bean
    public PasswordEncoder encoder() {

        return new BCryptPasswordEncoder();
    }


    private AuthenticationSuccessHandler successHandler() {
        SimpleUrlAuthenticationSuccessHandler simpleUrlAuthenticationSuccessHandler = new SimpleUrlAuthenticationSuccessHandler();
        simpleUrlAuthenticationSuccessHandler.setDefaultTargetUrl("/");
        return simpleUrlAuthenticationSuccessHandler;
    }


}
