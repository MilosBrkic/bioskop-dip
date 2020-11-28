package com.milosbrkic.bioskop.config;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author milos
 */
@Configuration
@EnableScheduling
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true, proxyTargetClass = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    
    @Autowired
    private UserDetailsService userDetailsService;

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        
        return new BCryptPasswordEncoder();
    }  

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.csrf().disable()
            .authorizeRequests()
            .antMatchers("/resources/**","/user/login.jsp","/register.jsp","/tiles*","/webjars/**",
                    "/home","/","/user/register","/user/confirm-account**","/user/resend-email",
                    "/film","/film/search","/film/{id}/view/","/film/getImage/{id}",
                    "/projekcija","/projekcija/search","/projekcija/{id}/view/",
                    "/distributer/{id}/view/","/sala/{id}/view/").permitAll()
            .antMatchers("/sala/**","/distributer/**","/film/**","/karta/{id}/sell").hasAuthority("ZAPOSLENI")
            .antMatchers("/user/registerZaposleni**").hasAuthority("ADMIN")
            .antMatchers("/karta/{id}/buy**","/karta/success","/karta/cancel").hasAuthority("KUPAC")
            .anyRequest().authenticated()
            .and()
            .formLogin()
            .loginPage("/user/login")
            .defaultSuccessUrl("/home")
            .failureUrl("/user/login?error")
            .permitAll()
            .and()
            .logout()
            .logoutUrl("/logout")
            .logoutSuccessUrl("/user/login?logout")
            .permitAll().and().headers().disable();
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder());
    }
    
}
