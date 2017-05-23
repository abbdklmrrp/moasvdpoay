package jtelecom.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

/**
 * @author Rysakova Anna
 */
@Component
public class SecurityAuthenticationHelper {
    /**
     * This method takes the current user in the context of the application.
     * If the user is not logged on <code> anonymousUser </code>
     * method return <code>null</code>
     *
     * @return current user
     */
    public User getCurrentUser() {
        Authentication authentication = getAuthentication();

        if (authentication == null) {
            return null;
        }

        Object user = authentication.getPrincipal();
        if (user instanceof String) {
            //anonymousUser
            return null;
        } else {
            return (User) user;
        }
    }

    private Authentication getAuthentication() {
        return SecurityContextHolder.getContext().getAuthentication();
    }

}
