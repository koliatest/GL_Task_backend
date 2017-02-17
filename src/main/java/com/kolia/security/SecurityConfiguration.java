package com.kolia.security;

import com.kolia.dao.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.access.AccessDecisionVoter;
import org.springframework.security.access.vote.AffirmativeBased;
import org.springframework.security.access.vote.AuthenticatedVoter;
import org.springframework.security.access.vote.RoleVoter;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.expression.WebExpressionVoter;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    private static String REALM="MY_TEST_REALM";

    // @Autowired
    // @Qualifier("userDetailsService")
    // UserDetailsService userDetailsService;

    @Autowired
    UserDao userDao;

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        /* auth.inMemoryAuthentication().withUser("bill").password("abc123").roles("ADMIN");
        auth.inMemoryAuthentication().withUser("tom").password("abc123").roles("USER"); */
        auth.userDetailsService(userDetailsService());//.passwordEncoder(passwordEncoder());
    }

    @Bean
    public UserDetailsService userDetailsService() {
        return new UserDetailsService() {

            @Override
            @Transactional(readOnly = true)
            public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
                com.kolia.model.User account = userDao.findByLogin(username);
                if(account != null) {
                    return new User(account.getLogin(), account.getPassword(), true, true, true, true,
                            AuthorityUtils.createAuthorityList("ADMIN"));
                } else {
                    throw new UsernameNotFoundException("could not find the user '"
                            + username + "'");
                }
            }

        };
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.csrf().disable()
                .authorizeRequests()
                .antMatchers("/").permitAll()
                .antMatchers("/api/**").fullyAuthenticated()
                .and().httpBasic().realmName(REALM).authenticationEntryPoint(getBasicAuthEntryPoint())
                .and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);//We don't need sessions to be created.
    }

    @Bean
    public CustomBasicAuthenticationEntryPoint getBasicAuthEntryPoint(){
        return new CustomBasicAuthenticationEntryPoint();
    }

    /* To allow Pre-flight [OPTIONS] request from browser */
    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers(HttpMethod.OPTIONS, "/**");
    }

    @Bean
    public PasswordEncoder passwordEncoder(){

        PasswordEncoder encoder = new BCryptPasswordEncoder();
        return encoder;
    }

    @Bean
    public AffirmativeBased accessDecisionManager() {
        return new AffirmativeBased(Arrays.asList(roleVoter(),
                webExpressionVoter(), (AccessDecisionVoter) authenticatedVoter()));
    }

    @Bean
    public RoleVoter roleVoter() {
        RoleVoter roleVoter = new RoleVoter();
        roleVoter.setRolePrefix("");
        return roleVoter;
    }

    @Bean
    public WebExpressionVoter webExpressionVoter() {
        return new WebExpressionVoter();
    }

    @Bean
    public AuthenticatedVoter authenticatedVoter() {
        return new AuthenticatedVoter();
    }
}
