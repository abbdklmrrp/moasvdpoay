package nc.nut.persistence;

import org.springframework.jdbc.core.PreparedStatementCreatorFactory;

/**
 * Created by Anna on 20.04.2017.
 */
class StatementFactory {
    static PreparedStatementCreatorFactory create(String sql, int... types) {
        PreparedStatementCreatorFactory factory = new PreparedStatementCreatorFactory(sql, types);
        factory.setReturnGeneratedKeys(true);
        return factory;
    }
}
