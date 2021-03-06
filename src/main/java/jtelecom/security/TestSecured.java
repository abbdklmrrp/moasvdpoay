package jtelecom.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Component;

/**
 * @author Anna Rysakova
 */
@Component
public class TestSecured {
    private static Logger logger = LoggerFactory.getLogger(TestSecured.class);

    @Secured("ADMIN")
    public void testSecured() {
        logger.info("Method called by role 'ADMIN'");
        System.out.println("Method called by role 'ADMIN'");
    }
}
