package com.example.capstone.pizza_delivery_service.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;



@Configuration
@EnableWebSecurity
public class WebSecurityConfig  {

    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(t->t
                .requestMatchers("/",
                        "/index",
                        "/addtocart/{ID}",
                        "/login","/signup",
                        "/css/*",
                        "/js/*",
                        "/images/*",
                        "/error",
                        "/pay",
                        "/signup",
                        "/error").permitAll()
                .requestMatchers("/orders","/customers","/customers/*").hasAuthority("ADMIN")
                .requestMatchers("/myOrders").hasAuthority("CUSTOMER")
                .anyRequest().authenticated())
                .formLogin(form->form.loginPage("/login")
                        .loginProcessingUrl("/login")
                        .successHandler(successHandler()))
                .logout(out -> out.logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                                  .permitAll())
                .authenticationProvider(authProvider());

        return http.build();

    }

    @Bean
    public UserDetailsService detailsService() {
        return new UserDetailsService();
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
