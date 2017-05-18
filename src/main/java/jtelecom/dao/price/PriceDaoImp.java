package jtelecom.dao.price;

import jtelecom.dto.PriceByRegionDto;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Yuliya Pedash on 27.04.2017.
 */
@Service
public class PriceDaoImp implements PriceDao {
    private final static String ADD_PRICE_OF_PRODUCT_BY_REGION = "INSERT INTO PRICES\n " +
            "VALUES(:productId,:placeId,:price)";
    private final static String FIND_PRICE_IN_REGIONS_FOR_ALL_PRODUCTS = "SELECT *\n" +
            "FROM (SELECT\n" +
            "        a.*,\n" +
            "        rownum rnum\n" +
            "      FROM (SELECT\n" +
            "              product.ID,\n" +
            "              product.NAME,\n" +
            "              product.DESCRIPTION,\n" +
            "              place.NAME PLACE,\n" +
            "              price.PRICE\n" +
            "            FROM PRODUCTS product\n" +
            "              JOIN PRICES price ON (product.ID = price.PRODUCT_ID)\n" +
            "              JOIN PLACES place ON (price.PLACE_ID = place.ID)\n" +
            "            WHERE product.NAME LIKE :pattern\n" +
            "                  OR description LIKE :pattern\n" +
            "                  OR place.NAME LIKE :pattern\n" +
            "                  OR PRICE LIKE :pattern\n" +
            "            ORDER BY %s ) a\n" +
            "      WHERE rownum <= :length)\n" +
            "WHERE rnum > :start";
    private final static String FIND_PRICE_IN_REGION_BY_PRODUCT = "SELECT \n" +
            "  product.ID,\n" +
            "  product.NAME,\n" +
            "  product.DESCRIPTION,\n" +
            "  place.NAME PLACE,\n" +
            "  place.ID PLACE_ID,\n" +
            "  price.PRICE\n" +
            "FROM PRODUCTS product\n" +
            "  JOIN PRICES price ON (product.ID = price.PRODUCT_ID)\n" +
            "  JOIN PLACES place ON (price.PLACE_ID = place.ID) WHERE product.ID=:productId";
    private final static String FIND_ALL_PLACES_AND_PRICE_AT_PLACES = "SELECT\n" +
            "  PLACES.ID,\n" +
            "  PLACES.NAME,\n" +
            "  price.PRICE\n" +
            "FROM places\n" +
            "  LEFT JOIN (SELECT *\n" +
            "             FROM prices\n" +
            "             WHERE PRODUCT_ID = :productId) price ON (places.id = price.PLACE_ID)\n" +
            "WHERE PLACES.PARENT_ID IS NOT NULL\n" +
            "ORDER BY PLACES.NAME";
    private final static String FIND_ALL_PRICE_INFO_BY_PRODUCT = "SELECT * FROM PRICES\n" +
            " WHERE PRODUCT_ID=:productId";
    private final static String DELETE_PRODUCT_PRICE_FROM_REGION = "DELETE FROM PRICES\n" +
            "WHERE PRODUCT_ID=:productId AND PLACE_ID=:placeId";
    private static String SELECT_PRICE_BY_PRODUCT_AND_PLACE_SQL = "SELECT PRICES.PLACE_ID, PRICES.PRODUCT_ID, PRICES.PRICE" +
            "  FROM PRICES WHERE PRODUCT_ID = :product_id AND PLACE_ID = :place_id";
    @Resource
    private NamedParameterJdbcTemplate jdbcTemplate;

    @Override
    public Price getById(int id) {
        return null;
    }

    @Override
    public boolean update(Price object) {
        return false;
    }

    @Override
    public boolean save(Price object) {
        return false;
    }

    @Override
    public boolean delete(Price object) {
        return false;
    }

    @Override
    public Price getPriceByProductIdAndPlaceId(Integer productId, Integer placeId) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("product_id", productId);
        params.addValue("place_id", placeId);
        return jdbcTemplate.queryForObject(SELECT_PRICE_BY_PRODUCT_AND_PLACE_SQL, params, new PriceRowMapper());
    }

    @Override
    public boolean fillPriceOfProductByRegion(List<Price> priceByRegion) {
        List<Map<String, Object>> batchValues = new ArrayList<>(priceByRegion.size());
        for (Price price : priceByRegion) {
            batchValues.add(
                    new MapSqlParameterSource("productId", price.getProductId())
                            .addValue("placeId", price.getPlaceId())
                            .addValue("price", price.getPrice())
                            .getValues());
        }
        int[] isInsert = jdbcTemplate.batchUpdate(ADD_PRICE_OF_PRODUCT_BY_REGION, batchValues.toArray(new Map[priceByRegion.size()]));
        return isInsert.length != 0;
    }

    /**
     * Anna Rysakova
     *
     * @param productId
     * @return
     */
    @Override
    public List<PriceByRegionDto> getPriceInRegionsByProduct(int productId) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("productId", productId);
        return jdbcTemplate.query(FIND_PRICE_IN_REGION_BY_PRODUCT, params, (rs, rowNum) -> {
            PriceByRegionDto priceByRegionDto = new PriceByRegionDto();
            priceByRegionDto.setProductId(rs.getInt("ID"));
            priceByRegionDto.setProductName(rs.getString("NAME"));
            priceByRegionDto.setProductDescription(rs.getString("DESCRIPTION"));
            priceByRegionDto.setPlaceName(rs.getString("PLACE"));
            priceByRegionDto.setPlaceId(rs.getInt("PLACE_ID"));
            priceByRegionDto.setPriceProduct(rs.getBigDecimal("PRICE"));
            return priceByRegionDto;
        });
    }

    @Override
    public List<PriceByRegionDto> getAllRegionsAndProductPriceInRegionByProductId(Integer productId) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("productId", productId);
        return jdbcTemplate.query(FIND_ALL_PLACES_AND_PRICE_AT_PLACES, params, (rs, rowNum) -> {
            PriceByRegionDto priceByRegionDto = new PriceByRegionDto();
            priceByRegionDto.setPlaceId(rs.getInt("ID"));
            priceByRegionDto.setProductId(productId);
            priceByRegionDto.setPlaceName(rs.getString("NAME"));
            priceByRegionDto.setPriceProduct(rs.getBigDecimal("PRICE"));
            return priceByRegionDto;
        });
    }

    @Override
    public List<Price> getPriceInRegionInfoByProduct(int productId) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("productId", productId);
        return jdbcTemplate.query(FIND_ALL_PRICE_INFO_BY_PRODUCT, params, (rs, rowNum) -> {
            Price price = new Price();
            price.setPlaceId(rs.getInt("PLACE_ID"));
            price.setProductId(rs.getInt("PRODUCT_ID"));
            price.setPrice(rs.getBigDecimal("PRICE"));
            return price;
        });
    }

    @Override
    public boolean deleteProductPriceInRegion(List<Price> priceInRegion) {
        List<Map<String, Object>> batchValues = new ArrayList<>(priceInRegion.size());
        for (Price price : priceInRegion) {
            batchValues.add(
                    new MapSqlParameterSource("productId", price.getProductId())
                            .addValue("placeId", price.getPlaceId())
                            .getValues());
        }
        int[] isInsert = jdbcTemplate.batchUpdate(DELETE_PRODUCT_PRICE_FROM_REGION, batchValues.toArray(new Map[priceInRegion.size()]));
        return isInsert.length != 0;
    }

    @Override
    public List<PriceByRegionDto> getLimitedQuantityProductPricesInRegions(int start, int length, String sort, String search) {
        int rownum = start + length;
        if (sort.isEmpty()) {
            sort = "ID";
        }
        String sql = String.format(FIND_PRICE_IN_REGIONS_FOR_ALL_PRODUCTS, sort);
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("start", start);
        params.addValue("length", rownum);
        params.addValue("pattern", "%" + search + "%");
        List<PriceByRegionDto> products = jdbcTemplate.query(sql, params, (rs, rowNum) -> {
            PriceByRegionDto priceByRegionDto = new PriceByRegionDto();
            priceByRegionDto.setProductId(rs.getInt("ID"));
            priceByRegionDto.setProductName(rs.getString("NAME"));
            priceByRegionDto.setProductDescription(rs.getString("DESCRIPTION"));
            priceByRegionDto.setPlaceName(rs.getString("PLACE"));
            priceByRegionDto.setPriceProduct(rs.getBigDecimal("PRICE"));
            return priceByRegionDto;
        });
        return products;
    }
}
