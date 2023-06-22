package com.mindhub.homebanking.configurations;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
@EnableWebSecurity
@Configuration
public class WebAuthorization extends WebSecurityConfigurerAdapter {

    @Override

    protected void configure(HttpSecurity http) throws Exception {

            http.authorizeRequests()
                    .antMatchers("/web/index.html").hasAuthority("CLIENT")


                    .antMatchers("/admin/**").hasAuthority("ADMIN")
                    .antMatchers("/web/pages/manager.html").hasAuthority("ADMIN")
                    .antMatchers("/web/pages/manager.html").hasAuthority("ADMIN")
                    .antMatchers("/h2/console").hasAuthority("ADMIN");


            http.formLogin()

                    .usernameParameter("name")

                    .passwordParameter("pwd")

                    .loginPage("/app/login");



            http.logout().logoutUrl("/app/logout");

    }

}
