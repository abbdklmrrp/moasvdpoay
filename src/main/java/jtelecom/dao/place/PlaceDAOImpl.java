package jtelecom.dao.place;

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
    private final static String GET_ALL_SQL = "SELECT * FROM Places";
    private final static String GET_PLACES_FOR_FILL_IN_TARIFF = "Select ID,NAME FROM PLACES WHERE \n" +
            "PARENT_ID IS NOT NULL ORDER BY NAME";
    private final static String FIND_PLACE_ID_BY_NAME = "SELECT ID FROM PLACES WHERE NAME=:placeName";
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

    @Override
    public Integer getPlaceIdByName(String placeName) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("placeName", placeName);
        return jdbcTemplate.queryForObject(FIND_PLACE_ID_BY_NAME, params, (rs, rowNum) -> rs.getInt("ID"));
    }
}
