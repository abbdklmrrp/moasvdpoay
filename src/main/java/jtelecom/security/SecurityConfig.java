package jtelecom.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PropertiesLoaderUtils;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.Map;
import java.util.Properties;

/**
 * @author Anna Rysakova
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Resource
    private UserDetailsService userDetailsService;
    @Resource
    private Md5PasswordEncoder md5PasswordEncoder;

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(md5PasswordEncoder);
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/resources/**");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.logout().logoutUrl("/doLogout").logoutSuccessUrl("/login.htm");
        http.csrf().disable();

        ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry registry =
                http.authorizeRequests();
        registry.antMatchers("/*").permitAll();

        Properties secured = readSecurityUrls();
        for (Map.Entry<Object, Object> e : secured.entrySet()) {
            String url = e.getKey().toString();
            String rolesString = e.getValue().toString();
            String[] roles = rolesString.split(",");
            registry.antMatchers(url).hasAnyAuthority(roles);
        }

        registry.and()
                .formLogin()
                .usernameParameter("username") //login form
                .passwordParameter("password")//login form
                .loginPage("/login.htm")
                .failureUrl("/failure.htm")
                .defaultSuccessUrl("/index.htm")
                .loginProcessingUrl("/doLogin")//login form
                .permitAll()
                .and()
                .exceptionHandling().accessDeniedPage("/accessDenied");
    }

    private Properties readSecurityUrls() {
        try {
            Properties properties = PropertiesLoaderUtils.loadAllProperties("security.properties");
            if (properties.isEmpty()) {
                throw new IllegalStateException("File security.properties is not exist or not valid");
            }
            return properties;
        } catch (IOException e) {
            throw new IllegalStateException("File security.properties is not exist or not valid");
        }
    }
}