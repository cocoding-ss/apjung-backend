package me.apjung.backend.config;

import me.apjung.backend.property.SecurityProps;
import me.apjung.backend.service.security.CustomUserDetailsService;
import me.apjung.backend.service.security.JwtTokenAuthenticationFilter;
import me.apjung.backend.service.security.AccessTokenProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    private final SecurityProps securityProps;
    private final CustomUserDetailsService customUserDetailsService;
    private final AccessTokenProvider accessTokenProvider;

    public SecurityConfig(SecurityProps securityProps, CustomUserDetailsService customUserDetailsService, AccessTokenProvider accessTokenProvider) {
        this.securityProps = securityProps;
        this.customUserDetailsService = customUserDetailsService;
        this.accessTokenProvider = accessTokenProvider;
    }

    @Bean
    public JwtTokenAuthenticationFilter tokenAuthenticationFilter() {
        return new JwtTokenAuthenticationFilter(accessTokenProvider, customUserDetailsService);
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return  new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(customUserDetailsService).passwordEncoder(passwordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors()
            .and()
            .headers().frameOptions().disable()
            .and()
            .httpBasic().disable()
            .formLogin().disable()
            .rememberMe().disable()
            .csrf().disable()
            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .and()
            .authorizeRequests()
                .antMatchers(securityProps.getPermittedEndpoints().stream().toArray(String[]::new)).permitAll()
                .anyRequest().authenticated();

        http.addFilterBefore(tokenAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
    }
}
