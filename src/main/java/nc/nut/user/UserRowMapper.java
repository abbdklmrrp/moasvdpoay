package nc.nut.user;


import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import static nc.nut.util.UserUtil.parseEnable;


/**
 * @author Sergiy Dyrda
 */
class UserRowMapper implements RowMapper<User> {

    @Override
    public User mapRow(ResultSet rs, int rowNum) throws SQLException {
        User user = new User();
        user.setId(rs.getInt("ID"));
        user.setName(rs.getString("NAME"));
        user.setSurname(rs.getString("SURNAME"));
        user.setEmail(rs.getString("EMAIL"));
        user.setPhone(rs.getString("PHONE"));
        user.setPassword(rs.getString("PASSWORD"));
        user.setAddress(rs.getString("ADDRESS"));
        user.setEnable(parseEnable(rs.getInt("ENABLE")));
        user.setRole(Enum.valueOf(Role.class, rs.getString("ROLE")));
        user.setPlace_id(rs.getLong("PLACE_ID"));
        user.setCustomer_id(rs.getLong("CUSTOMER_ID"));
        return user;
    }
}
