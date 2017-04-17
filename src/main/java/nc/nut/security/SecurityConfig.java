package nc.nut.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;

import javax.annotation.Resource;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Resource
    UserDetailsService userDetailsService;
    @Resource
    Md5PasswordEncoder md5PasswordEncoder;
    @Resource
    AuthenticationEventPublisherImpl authenticationEventPublisher;
    
    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationEventPublisher(authenticationEventPublisher);
        auth.userDetailsService(userDetailsService).passwordEncoder(md5PasswordEncoder);
    }
    
    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/assets/**");
    }
    
    //TODO: add matcher to roles
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.logout().logoutUrl("/doLogout").logoutSuccessUrl("/login.htm");
        http.csrf().disable();
        http.authorizeRequests()
//         .antMatchers("/**/*").hasAnyAuthority(Authority.ADMIN.getId())
                .anyRequest().permitAll()
                .and()
                .formLogin()
                .usernameParameter("username") //login form
                .passwordParameter("password")//login form
                .loginPage("/login.htm")
                .failureUrl("/failure.htm")
                .defaultSuccessUrl("/index.htm")
                .loginProcessingUrl("/doLogin")//login form
                .permitAll();
    }
    
    
}