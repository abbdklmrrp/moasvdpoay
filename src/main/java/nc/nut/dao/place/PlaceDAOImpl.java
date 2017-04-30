package nc.nut.dao.place;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.List;
/**
 * @author Revniuk Aleksandr
 */
@Repository
public class PlaceDAOImpl implements PlaceDAO {
    private final static String GET_ALL = "SELECT id, parent_id, name FROM Places";
    @Resource
    private NamedParameterJdbcTemplate jdbcTemplate;

    @Resource
    private PlaceRowMapper placeRowMapper;

    @Override
    public List<Place> getAll() {
        return jdbcTemplate.query(GET_ALL,placeRowMapper);
    }
}
