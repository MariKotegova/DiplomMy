package ru.netology.cloudstorage.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfiguration;

import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.SecurityFilterChain;
import ru.netology.cloudstorage.security.JwtConfigurer;
import ru.netology.cloudstorage.security.JwtTokenProvider;

@Configuration
@EnableWebSecurity
public class SecurityConf extends WebSecurityConfigurerAdapter {
//    private static final String LOGIN_ENDPOINT = "/login";

//
    private  JwtTokenProvider jwtTokenProvider;

    public SecurityConf() {

    }

    @Autowired
    public SecurityConf(JwtTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
    }

     @Bean
     @Override
    public AuthenticationManager authenticationManagerBean() throws Exception{
         return super.authenticationManagerBean();


      }

      @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                   .httpBasic().disable()
               .csrf().disable()
               .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
               .and()
               .authorizeRequests()
               .antMatchers("/login").permitAll()
                //.requestMatchers("/login").permitAll()
               .anyRequest().authenticated()
               .and()
               .apply(new JwtConfigurer(jwtTokenProvider));

          http.logout().logoutUrl("/logout");

    }
}
