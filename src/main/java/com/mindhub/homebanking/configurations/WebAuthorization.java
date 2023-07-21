package com.mindhub.homebanking.configurations;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.WebAttributes;
import org.springframework.security.web.authentication.logout.HttpStatusReturningLogoutSuccessHandler;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@EnableWebSecurity
@Configuration
public class WebAuthorization {

    @Bean
    protected SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

            http.authorizeRequests()
                    .antMatchers("/web/pages/manager.html").hasAuthority("ADMIN")
                    .antMatchers("/h2-console/login.do").hasAuthority("admin")
                    .antMatchers("/rest/**").hasAuthority("ADMIN")
                    .antMatchers(HttpMethod.GET, "/api/clients").hasAuthority("ADMIN")
                    .antMatchers("/web/pages/loan-admin.html").hasAuthority("ADMIN")


                    .antMatchers("/api/accounts/{id}").hasAuthority("CLIENT")
                    .antMatchers("/web/pages/accounts.html").hasAuthority("CLIENT")
                    .antMatchers("/web/pages/cards.html").hasAuthority("CLIENT")
                    .antMatchers("/web/pages/create-cards.html").hasAuthority("CLIENT")
                    .antMatchers("/web/pages/loan-application.html").hasAuthority("CLIENT")
                    .antMatchers("/web/pages/transfer.html").hasAuthority("CLIENT")
                    .antMatchers("/web/pages/account.html").hasAuthority("CLIENT")
                    .antMatchers(HttpMethod.POST, "/api/loans").hasAuthority("CLIENT")


                    .antMatchers(HttpMethod.GET, "/api/loans").permitAll()
                    .antMatchers(HttpMethod.POST, "/api/clients").permitAll()
                    .antMatchers("/web/index.html").permitAll()
                    .antMatchers("/web/pages/style/style.css").permitAll()
                    .antMatchers("/web/pages/js/**").permitAll()
                    .antMatchers("/web/pages/img/**").permitAll()
                    .antMatchers("/api/login").permitAll()
                    .antMatchers("/web/pages/contactUs.html").permitAll()
                    .antMatchers("/web/pages/aboutUs.html").permitAll();


            http.formLogin()

                    .usernameParameter("email")
                    .passwordParameter("password")

                    .loginPage("/api/login");

            http.logout()

                    .logoutUrl("/api/logout").deleteCookies("JSESSIONID");


        // turn off checking for CSRF tokens
        http.csrf().disable();

        //disabling frameOptions so h2-console can be accessed
        http.headers().frameOptions().disable();

        // if user is not authenticated, just send an authentication failure response
        http.exceptionHandling().authenticationEntryPoint((req, res, exc) -> res.sendError(HttpServletResponse.SC_UNAUTHORIZED));

        // if login is successful, just clear the flags asking for authentication
        http.formLogin().successHandler((req, res, auth) -> clearAuthenticationAttributes(req));

        // if login fails, just send an authentication failure response
        http.formLogin().failureHandler((req, res, exc) -> res.sendError(HttpServletResponse.SC_UNAUTHORIZED));

        // if logout is successful, just send a success response
        http.logout().logoutSuccessHandler(new HttpStatusReturningLogoutSuccessHandler());

        return http.build();
    }

    private void clearAuthenticationAttributes(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.removeAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);
        }
    }
}
