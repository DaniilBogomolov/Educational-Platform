package ru.itis.security.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AnonymousAuthenticationFilter;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.web.filter.GenericFilterBean;
import ru.itis.security.jwt.filter.JwtAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

    @Order(1)
    @Configuration
    public static class RestConfiguration extends WebSecurityConfigurerAdapter {

        @Autowired
        private AuthenticationProvider authenticationProvider;

        @Autowired
        @Qualifier("jwtAuthenticationFilter")
        private GenericFilterBean jwtAuthenticationFilter;

        @Override
        public void init(WebSecurity web) throws Exception {
            web.ignoring().antMatchers("/api/signIn");
        }

        @Override
        protected void configure(HttpSecurity http) throws Exception {
            http.csrf().disable();
            http.formLogin().disable();
            http.logout().disable();
            http.antMatcher("/api/**");
            http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
            http.addFilterBefore(jwtAuthenticationFilter, AnonymousAuthenticationFilter.class);
            http.authorizeRequests()
                    .antMatchers("/api/users").authenticated();
        }

        @Override
        @Autowired
        protected void configure(AuthenticationManagerBuilder auth) throws Exception {
            auth.authenticationProvider(authenticationProvider);
        }
    }


    @Order(2)
    @Configuration
    public static class HttpConfiguration extends WebSecurityConfigurerAdapter {

        @Autowired
        @Qualifier("httpDetailsService")
        private UserDetailsService userDetailsService;

        @Autowired
        private PasswordEncoder passwordEncoder;

        @Override
        protected void configure(HttpSecurity http) throws Exception {
            http.formLogin()
                    .loginPage("/signIn")
                    .defaultSuccessUrl("/home")
                    .failureUrl("/signIn")
                    .usernameParameter("login")
                    .permitAll();

            http.logout()
                    .logoutUrl("/logout")
                    .logoutSuccessUrl("/home");

            http.authorizeRequests()
                    .antMatchers("/signUp").anonymous()
                    .antMatchers("/profile", "/room/**", "/chat").authenticated()
                    .antMatchers("/home", "/confirm/**").permitAll()
                    .antMatchers("/room").hasAuthority("TEACHER")
                    .antMatchers("/files").hasAnyAuthority("STUDENT", "TEACHER");
        }

        @Override
        @Autowired
        protected void configure(AuthenticationManagerBuilder auth) throws Exception {
            auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder);
        }
    }
}
