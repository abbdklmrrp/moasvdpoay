package nc.nut.dao.place;

import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author Revniuk Aleksandr
 */
@Repository
public class PlaceDAOImpl implements PlaceDAO {
    private final static String GET_ALL_SQL = "SELECT * FROM Places";
    private final static String GET_PLACES_FOR_FILL_IN_TARIFF = "Select ID,NAME FROM PLACES WHERE ID<>1";
    @Resource
    private NamedParameterJdbcTemplate jdbcTemplate;

    @Resource
    private PlaceRowMapper placeRowMapper;

    @Override
    public List<Place> getAll() {
        return jdbcTemplate.query(GET_ALL_SQL, placeRowMapper);
    }

    @Override
    public List<Place> getPlacesForFillInTariff() {
        return jdbcTemplate.query(GET_PLACES_FOR_FILL_IN_TARIFF, (rs, rowNum) -> {
            Place place = new Place();
            place.setId(rs.getInt("ID"));
            place.setName(rs.getString("NAME"));
            return place;
        });
    }
}
