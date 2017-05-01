package nc.nut.dao.place;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by aleksandr on 27.04.17.
 */
@Component
public class PlaceRowMapper implements RowMapper<Place> {
    @Override
    public Place mapRow(ResultSet rs, int rowNum) throws SQLException {
        Place place = new Place();
        place.setId(rs.getInt("ID"));
        place.setParentId(rs.getInt("PARENT_ID"));
        place.setName(rs.getString("NAME"));
        return place;
    }
}
