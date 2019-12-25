package com.kamilmarnik.talkerr.security;

import com.kamilmarnik.talkerr.security.jwt.JwtAuthenticationFilter;
import com.kamilmarnik.talkerr.security.jwt.JwtConfig;
import com.kamilmarnik.talkerr.security.jwt.JwtLoginAuthenticationFilter;
import com.kamilmarnik.talkerr.user.domain.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.crypto.SecretKey;

@Configuration
@EnableWebSecurity
class SecurityConfiguration extends WebSecurityConfigurerAdapter {

  private final PasswordEncoder passwordEncoder;
  private final UserDetailsServiceImpl userDetailsService;
  private final SecretKey secretKey;
  private final JwtConfig jwtConfig;

  @Autowired
  public SecurityConfiguration(PasswordEncoder passwordEncoder, UserDetailsServiceImpl userDetailsService, SecretKey secretKey, JwtConfig jwtConfig) {
    this.passwordEncoder = passwordEncoder;
    this.userDetailsService = userDetailsService;
    this.secretKey = secretKey;
    this.jwtConfig = jwtConfig;
  }

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http
        .csrf().disable()
        .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        .and()
        .addFilter(new JwtLoginAuthenticationFilter(authenticationManager(), jwtConfig, secretKey))
        .addFilterAfter(new JwtAuthenticationFilter(secretKey, jwtConfig), JwtLoginAuthenticationFilter.class)
        .authorizeRequests()
        .anyRequest()
        .authenticated();
  }

  private DaoAuthenticationProvider authenticationProvider() {
    DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
    authProvider.setUserDetailsService(userDetailsService);
    authProvider.setPasswordEncoder(passwordEncoder);

    return authProvider;
  }

  @Override
  protected void configure(AuthenticationManagerBuilder auth) {
    auth.authenticationProvider(authenticationProvider());
  }

  @Override
  public void configure(WebSecurity web) {
    web.ignoring().antMatchers("/register");
  }
}
