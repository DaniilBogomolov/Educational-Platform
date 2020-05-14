package ru.itis.security.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
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
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.session.jdbc.config.annotation.web.http.EnableJdbcHttpSession;
import org.springframework.web.filter.GenericFilterBean;
import ru.itis.security.jwt.filter.JwtAuthenticationFilter;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
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

        @Autowired
        private DataSource dataSource;

        @Bean
        public PersistentTokenRepository persistentTokenRepository() {
            JdbcTokenRepositoryImpl jdbcTokenRepository = new JdbcTokenRepositoryImpl();
            jdbcTokenRepository.setDataSource(dataSource);
            return jdbcTokenRepository;
        }

        @Override
        protected void configure(HttpSecurity http) throws Exception {
            http.formLogin()
                    .loginPage("/signIn")
                    .defaultSuccessUrl("/home")
                    .failureUrl("/signIn")
                    .usernameParameter("login")
                    .permitAll();

            http.logout().logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                    .logoutSuccessUrl("/home")
                    .deleteCookies("JSESSIONID", "remember-me")
                    .invalidateHttpSession(true);

            http.authorizeRequests()
                    .antMatchers("/signUp").anonymous()
                    .antMatchers("/profile", "/room/**", "/chat").authenticated()
                    .antMatchers("/home", "/confirm/**").permitAll()
                    .antMatchers("/room").hasAuthority("TEACHER")
                    .antMatchers("/files").hasAnyAuthority("STUDENT", "TEACHER")
            .and()
            .rememberMe().rememberMeParameter("remember-me").tokenRepository(persistentTokenRepository());
        }

        @Override
        @Autowired
        protected void configure(AuthenticationManagerBuilder auth) throws Exception {
            auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder);
        }
    }
}
