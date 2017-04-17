package nc.nut.domain;

import nc.nut.security.AuthorityConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    Logger logger = LoggerFactory.getLogger(getClass());
    
    @Override
    public User findByName(String name) {
        logger.info("Searching by name {}", name);
        // TODO: get from DB
        if ("admin".equals(name)) {
            // password: admin
            // 58bd1d8f8a93b0b6c12ab4ed747567f3 <- "admin"
            return new User(name, "58bd1d8f8a93b0b6c12ab4ed747567f3", AuthorityConstants.ADMIN_VALUE);
        }
        if ("manager".equals(name)) {
            // password: manager
            // 2d889e183be0dd25b335fdd5ec92002b <- "manager"
            return new User(name, "2d889e183be0dd25b335fdd5ec92002b", AuthorityConstants.MANAGER_VALUE);
        }
        if ("support".equals(name)) {
            // password: support
            // 1d9e132d3a2fc27bc5fb819098038e6c <- "support"
            return new User(name, "1d9e132d3a2fc27bc5fb819098038e6c", AuthorityConstants.SUPPORT_VALUE);
        } else {
            // password: user
            // 58bd1d8f8a93b0b6c12ab4ed747567f3 <- "user"
            return new User(name, "2cd613d62f1988c770eecd11f6616801", AuthorityConstants.USER_VALUE);
        }
    }
}
