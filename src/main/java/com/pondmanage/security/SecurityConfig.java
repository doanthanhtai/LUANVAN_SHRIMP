package com.pondmanage.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@EnableWebSecurity
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Bean
    public UserDetailsService userDetailsService() {
        return new AccountDetailsServiceImp();
    }

    @Bean
    BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService());
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();
        http.authorizeRequests()// phan quyen truy cap url
                .antMatchers("/resources/**").permitAll()
                .antMatchers("/js/**", "/css/**", "/img/**").permitAll() // 38 39 cho phep truy cập tai fnguyeen css/js/static image
                .antMatchers("/login", "/register", "/changePassword", "/reset-password").permitAll() // cho phép các trang đăng ký được truy cập
                //.antMatchers("/**").permitAll()
                .anyRequest().authenticated() //bao giờ sửa luồng đăng nhập thì xoá /changePassword đi
                .and()
                .formLogin()
                .loginPage("/login")
                .usernameParameter("email")
                .loginProcessingUrl("/authenticate") // trỏ cái url của cái form đăng ký vào url này
                .defaultSuccessUrl("/zones")
                .permitAll()
                .and()
                .logout()
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                .logoutSuccessUrl("/login")
                .and()
                .exceptionHandling().accessDeniedPage("/403");
        ;
    }
}
