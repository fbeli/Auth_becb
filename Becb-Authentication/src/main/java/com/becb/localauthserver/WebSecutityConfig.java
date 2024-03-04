package com.becb.localauthserver;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class WebSecutityConfig extends WebSecurityConfigurerAdapter {

    /* para testes em memória

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {

        auth.inMemoryAuthentication()
                .withUser("frederico")
                .password(passwordEncoder().encode("123"))
                .roles("ADMIN")
                .and()
                .withUser("joao")
                .password(passwordEncoder().encode("123"))
                .roles("ADMIN");
    }*/

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    @Override
    protected AuthenticationManager authenticationManager() throws Exception {
        return super.authenticationManager();
    }

    /* usado para usuáris em memória

    @Bean
     @Override
     protected UserDetailsService userDetailsService() {
         return super.userDetailsService();
     }
 */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                .antMatchers(HttpMethod.GET, "/oauth/token").permitAll()
                .antMatchers(HttpMethod.GET, "/oauth/check_token").permitAll()
                .antMatchers(HttpMethod.GET, "/health").permitAll()
                .antMatchers("/**").permitAll()
                .anyRequest().fullyAuthenticated()
                .and()
                .httpBasic()
                .and()
                .cors()
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .csrf().disable();
    }

}