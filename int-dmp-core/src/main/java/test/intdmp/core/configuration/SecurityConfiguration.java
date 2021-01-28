package test.intdmp.core.configuration;


import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {// @formatter:off
        http.cors()
                .and()
                .authorizeRequests()
                .antMatchers(HttpMethod.GET, "/user/info", "/api/projects/**")
                .hasAuthority("ROLE_Client_normal")
                .antMatchers(HttpMethod.POST, "/api/projects")
                .hasAuthority("SCOPE_write")
                .anyRequest()
                .authenticated()
                .and()
                .oauth2ResourceServer()
                .jwt();
    }//@formatter:on


}
