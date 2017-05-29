package jtelecom.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Component;

/**
 * Created by Anna Rysakova on 27.05.2017.
 */
@Component
public class TestSecured {
    private Logger logger = LoggerFactory.getLogger(getClass());

    @Secured("ADMIN")
    public void testSecured() {
        logger.info("Method called by role 'ADMIN'");
        System.out.println("Method called by role 'ADMIN'");
    }
}
