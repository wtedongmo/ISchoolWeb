//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.tsoft.config;

import com.tsoft.security.access.CustomPermissionEvaluator;
import com.tsoft.security.http.AuthFailureHandler;
import com.tsoft.security.http.AuthSuccessHandler;
import com.tsoft.security.http.CustomDaoAuthenticationProvider;
import com.tsoft.security.http.HttpAuthenticationEntryPoint;
import com.tsoft.security.http.HttpLogoutSuccessHandler;
import com.tsoft.security.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.access.expression.method.DefaultMethodSecurityExpressionHandler;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.encoding.ShaPasswordEncoder;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configurers.FormLoginConfigurer;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer.AuthorizedUrl;
import org.springframework.security.config.annotation.web.servlet.configuration.EnableWebMvcSecurity;

@Configuration
@EnableWebMvcSecurity
@EnableGlobalMethodSecurity(
    prePostEnabled = true
)
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private HttpAuthenticationEntryPoint authenticationEntryPoint;
    @Autowired
    private AuthSuccessHandler authSuccessHandler;
    @Autowired
    private AuthFailureHandler authFailureHandler;
    @Autowired
    private HttpLogoutSuccessHandler logoutSuccessHandler;
    @Autowired
    private UserService us;

    public SecurityConfig() {
    }

    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    public AuthenticationProvider authenticationProvider() throws Exception {
        CustomDaoAuthenticationProvider authenticationProvider = new CustomDaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(this.us);
        authenticationProvider.setPasswordEncoder(new ShaPasswordEncoder());
        return authenticationProvider;
    }

    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(this.authenticationProvider());
    }

    protected AuthenticationManager authenticationManager() throws Exception {
        return super.authenticationManager();
    }

    public void configure(WebSecurity builder) throws Exception {
        builder.ignoring().antMatchers(new String[]{"/app/css/**", "/app/fonts/**", "/app/img/**", "/app/js/**", "/app/l10n/**", "/app/tpl/**", "/app/vendor/**", "/app/index.html"});
    }

    protected void configure(HttpSecurity http) throws Exception {
        ((HttpSecurity)((HttpSecurity)((FormLoginConfigurer)((FormLoginConfigurer)((FormLoginConfigurer)((FormLoginConfigurer)((HttpSecurity)((HttpSecurity)http.csrf().disable()).authenticationProvider(this.authenticationProvider()).exceptionHandling().authenticationEntryPoint(this.authenticationEntryPoint).and()).formLogin().permitAll()).loginProcessingUrl("/app/connect")).usernameParameter("username").passwordParameter("password").successHandler(this.authSuccessHandler)).failureHandler(this.authFailureHandler)).and()).logout().logoutUrl("/app/signout").invalidateHttpSession(true).permitAll().logoutSuccessHandler(this.logoutSuccessHandler).and()).sessionManagement().maximumSessions(1);
        ((AuthorizedUrl)((AuthorizedUrl)((AuthorizedUrl)((AuthorizedUrl)((AuthorizedUrl)http.authorizeRequests().antMatchers(HttpMethod.GET, new String[]{"/app/init"})).permitAll().antMatchers(HttpMethod.GET, new String[]{"/app/demo"})).permitAll().antMatchers(HttpMethod.POST, new String[]{"/app/forgotpassword"})).permitAll().antMatchers(HttpMethod.POST, new String[]{"/app/changepasswordexp"})).permitAll().anyRequest()).authenticated();
    }

    @Bean(
        name = {"expressionHandler"}
    )
    DefaultMethodSecurityExpressionHandler getDefaultMethodSecurityExpressionHandler() {
        DefaultMethodSecurityExpressionHandler dmseh = new DefaultMethodSecurityExpressionHandler();
        dmseh.setPermissionEvaluator(new CustomPermissionEvaluator());
        return dmseh;
    }
}
