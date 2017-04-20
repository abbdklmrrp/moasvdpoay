package nc.nut.persistence;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreatorFactory;

import javax.annotation.Resource;

/**
 * Created by Anna on 20.04.2017.
 */
public abstract class BaseDAOImpl {
    @Resource
    protected JdbcTemplate jdbcTemplate;

    protected static PreparedStatementCreatorFactory create(String sql, int... types) {
        return StatementFactory.create(sql, types);
    }
}
