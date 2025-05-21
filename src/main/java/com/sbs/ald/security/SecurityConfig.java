package com.sbs.ald.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.sbs.ald.filter.JwtAuthenticationFilter;


@Configuration
@EnableMethodSecurity
public class SecurityConfig implements WebMvcConfigurer {  // implementira WebMvcConfigurer

    @Autowired
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    @Override  // Obavezno, kako bi Spring prepoznao ovu metodu
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("http://localhost:3000")
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                .allowedHeaders("*")
                .allowCredentials(true);
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .cors()  // CORS podrška
        .and()
        
            .csrf().disable()
            .sessionManagement(session -> session
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)) // Stateless autentifikacija
            .authorizeHttpRequests(auth -> auth
            		.requestMatchers(
            			    "/ws/**",
            			    "/ws/info/**"
            			).permitAll()

            		 .requestMatchers(
                    "/register",
                    "/api/auth/*",
                    "/api/osobe/**",
                    "/api/users/**",
                    "/user/login",
                    "/static/**",
                    "/h2/**"
                ).permitAll()
            		 .requestMatchers(
            				 "/",
                             "/index.html",
                             "/static/**",
                             "/assets",
                             "/js/**",
                             "/css/**",
                             "/assets/**",
                             "/favicon.ico",
                             "/manifest.json"
            			    ).permitAll()

                .anyRequest().authenticated()
            );
        http.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }
//    @Bean
//    public WebSecurityCustomizer webSecurityCustomizer() {
//        return (web) -> web.ignoring().requestMatchers(
//            "/js/**",
//            "/css/**",
//            "/assets/**",
//            "/index.html",
//            "/static/**"
//        );
//    }
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
