package com.biruk.habeshaJobs.SecurityConfig;

import com.biruk.habeshaJobs.DAO.UserDAO;
import com.biruk.habeshaJobs.Exceptions.EmailNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class WebSecurityConfig {

    private final JwtFilter jwtFilter;
    private final UserDAO userDAO;
    private AuthenticationManagerBuilder authenticationManagerBuilder;

    @Autowired
    public WebSecurityConfig(JwtFilter jwtFilter, UserDAO userDAO, AuthenticationManagerBuilder authenticationManagerBuilder) {
        this.jwtFilter = jwtFilter;
        this.userDAO = userDAO;
        this.authenticationManagerBuilder = authenticationManagerBuilder;
    }

    // our user class should implement UserDetails interface in order to remove the error because the spring security userDetailsService requires to return a userDetail object.
    @Autowired
    public void configureGlobal (AuthenticationManagerBuilder auth) throws Exception {

        auth.userDetailsService(email -> userDAO.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("Email: " + email + " not found.")));
    }

    //after authentication manager is configured above, we can use this method to get an authentication manager, which is used in login method
    @Bean
    public AuthenticationManager authenticationManager (AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    //this method is used to encode the password and other sensitive data that will be stored in the database.
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


    @Bean
    public SecurityFilterChain configure (HttpSecurity http) throws Exception {
        return http.csrf(c -> c.disable())
                .authorizeHttpRequests(auth ->
                        auth.requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                                .requestMatchers("/auth/**","/verify").permitAll()
                                .requestMatchers(new AntPathRequestMatcher("/jobs")).hasAuthority("Employer")
                                .anyRequest().authenticated()
                ).sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
        .build();
    }
}
