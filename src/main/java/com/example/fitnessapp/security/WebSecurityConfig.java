package com.example.fitnessapp.security;

import com.example.fitnessapp.security.models.AuthorizationRules;
import com.example.fitnessapp.security.models.Rule;
import com.example.fitnessapp.services.JwtUserDetailsService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;
import org.springframework.security.config.core.GrantedAuthorityDefaults;
import org.springframework.web.filter.CorsFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@EnableWebSecurity
@Configuration
public class WebSecurityConfig {
    private final AuthorizationFilter authorizationFilter;
    private final JwtUserDetailsService userDetailsService;
    private static final String FRONT_URL = "http://localhost:4200";

    @Autowired
    public WebSecurityConfig(AuthorizationFilter authorizationFilter,
                             JwtUserDetailsService userDetailsService) {
        this.authorizationFilter = authorizationFilter;
        this.userDetailsService = userDetailsService;
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity httpSecurity, PasswordEncoder passwordEncoder, UserDetailsService userDetailsService) throws Exception{
        AuthenticationManagerBuilder authenticationManagerBuilder = httpSecurity.getSharedObject(AuthenticationManagerBuilder.class);
        authenticationManagerBuilder.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder);
        return authenticationManagerBuilder.build();
    }

    @Bean
    public SecurityFilterChain configure(HttpSecurity httpSecurity) throws Exception{
        httpSecurity
                .cors(Customizer.withDefaults())
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement((session) -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
        createAuthorizationRules(httpSecurity);
        httpSecurity.addFilterBefore(authorizationFilter, UsernamePasswordAuthenticationFilter.class);
        return httpSecurity.build();
    }

    private void createAuthorizationRules(HttpSecurity httpSecurity) throws Exception{
        AuthorizationRules authorizationRules = new ObjectMapper().readValue(new ClassPathResource("rules.json").getInputStream(), AuthorizationRules.class);
        ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry interceptor = httpSecurity.authorizeRequests();
        interceptor = interceptor
                .requestMatchers(HttpMethod.POST, "/login").permitAll()
                .requestMatchers(HttpMethod.POST, "/register").permitAll()
                .requestMatchers(HttpMethod.GET, "/activate").permitAll()
                .requestMatchers(HttpMethod.GET, "/programs/{id}").permitAll()
                .requestMatchers(HttpMethod.GET, "/programs/user/{userId}").permitAll()
                .requestMatchers(HttpMethod.GET, "/programs").permitAll()
                .requestMatchers(HttpMethod.GET, "/categories").permitAll()
                .requestMatchers(HttpMethod.GET, "/attributes/bycategory/{categoryId}").permitAll()
                .requestMatchers(HttpMethod.GET, "/attributes/{id}/values").permitAll()
                .requestMatchers(HttpMethod.POST, "/comments").permitAll()
                .requestMatchers(HttpMethod.GET, "/comments/by_program/{programId}").permitAll()
                .requestMatchers(HttpMethod.GET, "/program_has_attribute_values/value/{valueId}").permitAll()
                .requestMatchers(HttpMethod.GET, "/program_has_attribute_values/program/{programId}").permitAll()
                .requestMatchers(HttpMethod.POST, "/programs/filter").permitAll();

        for (Rule rule : authorizationRules.getRules()){
            if(rule.getMethods().isEmpty())
                interceptor = interceptor.requestMatchers(rule.getPattern()).hasAnyAuthority(rule.getRoles().toArray(String[]::new));
            else for (String method : rule.getMethods()){
                interceptor = interceptor.requestMatchers(HttpMethod.valueOf(method), rule.getPattern()).hasAnyAuthority(rule.getRoles().toArray(String[]::new));
            }
        }
        interceptor.anyRequest().denyAll().and();
    }

    @Bean
    public CorsFilter corsFilter(){
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowCredentials(true);
        configuration.setAllowedOrigins(List.of(FRONT_URL));
        configuration.setAllowedHeaders(List.of("*"));
        configuration.setAllowedMethods(List.of("*"));
        source.registerCorsConfiguration("/**", configuration);
        return new CorsFilter(source);
    }

    @Bean
    GrantedAuthorityDefaults grantedAuthorityDefaults() {
        return new GrantedAuthorityDefaults("");
    }
}
