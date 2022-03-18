package com.team1.pinterest.Config;

import com.team1.pinterest.Entitiy.UserRole;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@RequiredArgsConstructor
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    private final CustomOAuth2UserService customOAuth2UserService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .headers().frameOptions().disable()
                .and()
                .authorizeRequests()
                .antMatchers("/", "/css/**", "/images/**", "/js/**", "h2-console/**").permitAll()
                .antMatchers(HttpMethod.POST,"/**").hasRole(UserRole.USER.name())
                .antMatchers(HttpMethod.PUT,"/**").hasRole(UserRole.USER.name())
                .antMatchers(HttpMethod.PATCH,"/**").hasRole(UserRole.USER.name())
                .antMatchers(HttpMethod.DELETE,"/**").hasRole(UserRole.USER.name())
                .antMatchers(HttpMethod.GET,"/**").permitAll()
                .anyRequest().authenticated()
                .and()
                .logout().logoutSuccessUrl("/")
                .and()
                .oauth2Login()
                .userInfoEndpoint()
                .userService(customOAuth2UserService);
    }
}
