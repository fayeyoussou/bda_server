package sn.youdev.config.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import sn.youdev.config.security.filter.AuthenticationFilter;
import sn.youdev.config.security.filter.AuthorizationFilter;
import sn.youdev.config.security.handler.AuthenticationFailHandler;
import sn.youdev.config.security.handler.AuthenticationSuccessHandler;
import sn.youdev.repository.TokenRepo;

import static sn.youdev.config.Constante.*;

@Configuration
public class SecurityConfig {
    private final AuthenticationManager authenticationManager;
    private final AuthenticationSuccessHandler authenticationSuccessHandler;
    private final AuthenticationFailHandler authenticationFailHandler;
    private final TokenRepo tokenRepo;

    @Autowired
    public SecurityConfig(AuthenticationManager authenticationManager, AuthenticationSuccessHandler authenticationSuccessHandler, AuthenticationFailHandler authenticationFailHandler, TokenRepo tokenRepo) {
        this.authenticationManager = authenticationManager;
        this.authenticationSuccessHandler = authenticationSuccessHandler;
        this.authenticationFailHandler = authenticationFailHandler;
        this.tokenRepo = tokenRepo;
    }
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .cors()
                .and()
                .csrf()
                .disable()
                .authorizeHttpRequests((auth)->{

                    try {
                        auth
//                                .antMatchers(AUTH_LIST).authenticated()
                                .antMatchers("/api/auth/**","/api/test/**","/api/test/upload","/api/file/**","/api/article/**").permitAll()
                                .antMatchers(ADMIN_LIST).hasAuthority("admin")
                                .antMatchers(CNTS_LIST).hasAuthority("cnts")
                                .anyRequest().authenticated()
                                .and()
                                .sessionManagement()
                                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                                .and()
                                .addFilter(authenticationFilter())
                                .addFilter(new AuthorizationFilter(authenticationManager,tokenRepo))
                                .exceptionHandling()
                                .authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED));
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                })
                .httpBasic(Customizer.withDefaults());
        return http.build();
    }
    @Bean
    public AuthenticationFilter authenticationFilter() {
        AuthenticationFilter filter = new AuthenticationFilter();
        filter.setAuthenticationSuccessHandler(authenticationSuccessHandler);
        filter.setAuthenticationFailureHandler(authenticationFailHandler);
        filter.setAuthenticationManager(authenticationManager);
        return filter;
    }
}
