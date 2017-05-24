package jtelecom.dao.place;

import jtelecom.dao.product.ProductStatus;
import jtelecom.dto.PriceByRegionDto;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.List;

/**
 * This class implements methods from PlaceDAO.
 *
 * @author Revniuk Aleksandr
 */
@Repository
public class PlaceDAOImpl implements PlaceDAO {
    private final static String GET_ALL_SQL = "SELECT * FROM Places";
    private final static String GET_PLACES_FOR_FILL_IN_TARIFF = "Select ID,NAME FROM PLACES WHERE \n" +
            "PARENT_ID IS NOT NULL ORDER BY NAME";
    private final static String FIND_PLACE_ID_BY_NAME = "SELECT ID FROM PLACES WHERE NAME=:placeName";
    private final static String FIND_PLACE_NAME_BY_ID = "SELECT NAME FROM PLACES WHERE ID=:placeId";
    private final static String SELECT_LIMITED_PLACES = "SELECT *\n" +
            "FROM (SELECT\n" +
            "        a.*,\n" +
            "        rownum rnum\n" +
            "      FROM (SELECT *\n" +
            "            FROM PLACES\n" +
            "            WHERE PLACES.NAME LIKE :pattern AND PLACES.PARENT_ID IS NOT NULL\n" +
            "            ORDER BY %s) a\n" +
            "      WHERE rownum <= :length)\n" +
            "WHERE rnum > :start";
    private static final String SELECT_COUNT = "SELECT count(ID)\n" +
            "  FROM PLACES\n" +
            " WHERE NAME LIKE :pattern ";
    private final static String SELECT_LIMITED_PRICES_BY_PLACE = "SELECT *\n" +
            "FROM (SELECT\n" +
            "        a.*,\n" +
            "        rownum rnum\n" +
            "      FROM (SELECT\n" +
            "              product.ID,\n" +
            "              type.NAME      TYPE,\n" +
            "              product.NAME   PRODUCT_NAME,\n" +
            "              product.STATUS STATUS,\n" +
            "              price.PRICE,\n" +
            "              price.PLACE_ID,\n" +
            "              place.NAME     PLACE\n" +
            "            FROM\n" +
            "              PRODUCTS product\n" +
            "              JOIN PRODUCT_TYPES type ON (product.TYPE_ID = type.ID)\n" +
            "              JOIN PRICES price ON (price.PRODUCT_ID = product.ID)\n" +
            "              JOIN PLACES place ON (price.PLACE_ID = place.ID)\n" +
            "            WHERE place.ID = :placeId AND\n" +
            "                  (product.NAME LIKE :pattern\n" +
            "                  OR STATUS LIKE :pattern\n" +
            "                  OR PRICE LIKE :pattern\n" +
            "                  OR type.NAME LIKE :pattern)\n" +
            "            ORDER BY %s) a\n" +
            "      WHERE rownum <= :length)\n" +
            "WHERE rnum > :start";
    private static final String SELECT_COUNT_PRICE_BY_PLACE = "SELECT count(product.ID)\n" +
            "FROM\n" +
            "  PRODUCTS product\n" +
            "  JOIN PRODUCT_TYPES type ON (product.TYPE_ID = type.ID)\n" +
            "  JOIN PRICES price ON (price.PRODUCT_ID = product.ID)\n" +
            "  JOIN PLACES place ON (price.PLACE_ID = place.ID)\n" +
            "WHERE place.ID = :placeId AND\n" +
            "      (product.NAME LIKE :pattern\n" +
            "       OR STATUS LIKE :pattern\n" +
            "       OR PRICE LIKE :pattern OR type.NAME LIKE :pattern)";
    @Resource
    private NamedParameterJdbcTemplate jdbcTemplate;

    @Resource
    private PlaceRowMapper placeRowMapper;

    /**
     * Revniuk Aleksandr
     * {@inheritDoc}
     */
    @Override
    public List<Place> getAll() {
        return jdbcTemplate.query(GET_ALL_SQL, placeRowMapper);
    }

    @Override
    public List<Place> getAllPlaces() {
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

    @Override
    public List<Place> getLimitedQuantityPlace(int start, int length, String sort, String search) {
        int rownum = start + length;
        if (sort.isEmpty()) {
            sort = "ID";
        }
        String sql = String.format(SELECT_LIMITED_PLACES, sort);
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("start", start);
        params.addValue("length", rownum);
        params.addValue("pattern", "%" + search + "%");
        return jdbcTemplate.query(sql, params, new PlaceRowMapper());
    }

    @Override
    public Integer getCountPlacesWithSearch(String search) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("pattern", "%" + search + "%");
        return jdbcTemplate.queryForObject(SELECT_COUNT, params, Integer.class);
    }

    @Override
    public List<PriceByRegionDto> getLimitedQuantityPriceByPlace(int placeId, int start, int length, String sort, String search) {
        int rownum = start + length;
        if (sort.isEmpty()) {
            sort = "ID";
        }
        String sql = String.format(SELECT_LIMITED_PRICES_BY_PLACE, sort);
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("start", start);
        params.addValue("length", rownum);
        params.addValue("pattern", "%" + search + "%");
        params.addValue("placeId", placeId);
        return jdbcTemplate.query(sql, params, (rs, rowNum) -> {
            PriceByRegionDto priceByPlace = new PriceByRegionDto();
            priceByPlace.setProductId(rs.getInt("ID"));
            priceByPlace.setProductName(rs.getString("PRODUCT_NAME"));
            priceByPlace.setProductType(rs.getString("TYPE"));
            priceByPlace.setProductStatus(ProductStatus.getProductStatusFromId(rs.getInt("STATUS")).getName());
            priceByPlace.setPlaceName(rs.getString("PLACE"));
            priceByPlace.setPriceProduct(rs.getBigDecimal("PRICE"));
            priceByPlace.setPlaceId(placeId);

            return priceByPlace;
        });
    }

    @Override
    public String getPlaceNameById(int id) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("placeId", id);
        return jdbcTemplate.queryForObject(FIND_PLACE_NAME_BY_ID, params, String.class);
    }

    @Override
    public Integer getCountPriceByPlace(String search, Integer placeId) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("pattern", "%" + search + "%");
        params.addValue("placeId", placeId);
        return jdbcTemplate.queryForObject(SELECT_COUNT_PRICE_BY_PLACE, params, Integer.class);
    }
}
