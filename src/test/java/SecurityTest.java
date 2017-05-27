import jtelecom.config.SpringConfig;
import jtelecom.security.TestSecured;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@WebAppConfiguration
@DirtiesContext
@ContextConfiguration(classes = {SpringConfig.class})
@RunWith(SpringJUnit4ClassRunner.class)
public class SecurityTest {

    @Resource
    private UserDetailsService userDetailsService;
    @Resource
    private TestSecured testSecured;

    /**
     * Test the valid user with valid role
     */
    @Test
    public void testDataSource() {
        UserDetails userDetails = userDetailsService.loadUserByUsername("admin");
        Authentication authToken = new UsernamePasswordAuthenticationToken(userDetails.getUsername(), userDetails.getPassword(), userDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authToken);
    }

    /**
     * Test the valid user with INVALID role
     */
    @Test(expected = AccessDeniedException.class)
    public void testInvalidRole() {
        UserDetails userDetails = userDetailsService.loadUserByUsername("admin");
        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("ROLE_INVALID"));
        Authentication authToken = new UsernamePasswordAuthenticationToken(userDetails.getUsername(), userDetails.getPassword(), authorities);

        SecurityContextHolder.getContext().setAuthentication(authToken);
        testSecured.testSecured();
    }

    /**
     * Test the INVALID user
     */
    @Test(expected = UsernameNotFoundException.class)
    public void testInvalidUser() {
        UserDetails userDetails = userDetailsService.loadUserByUsername("invalid_user");
        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("ROLE_INVALID"));
        Authentication authToken = new UsernamePasswordAuthenticationToken(userDetails.getUsername(), userDetails.getPassword(), authorities);
        SecurityContextHolder.getContext().setAuthentication(authToken);
        testSecured.testSecured();
    }
}
