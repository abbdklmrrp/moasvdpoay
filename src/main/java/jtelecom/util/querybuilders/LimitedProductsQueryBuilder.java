package jtelecom.util.querybuilders;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;

/**
 * Created by Yuliya Pedash on 15.05.2017.
 */
public class LimitedProductsQueryBuilder {
    private final static StringBuffer SELECT_LIMITED_SERVICES_FOR_BUSINESS_SQL = new StringBuffer("SELECT\n" +
            "  products.*,\n" +
            "  rownum\n" +
            "FROM products\n" +
            "WHERE rownum <= :length AND rownum > :start AND TYPE_ID = 2 /*Service*/ AND status = 1 /*Active*/");
    private static final String SEARCH_SQL = " AND LOWER(name) LIKE LOWER('%s') || '%%' ";
    private static final String CATEGORY_SQL = " AND category_id = ";
    private static final String ORDER_BY_SQL = " ORDER BY ";
    private static MapSqlParameterSource params = new MapSqlParameterSource();

    public static String getQuery(String query, String sort, String search, Integer category) {
//        params.addValue("start", start);
//        params.addValue("length", length);
        StringBuffer queryStringBuffer = new StringBuffer(query);
        if (!search.isEmpty()) {
            queryStringBuffer.append(String.format(SEARCH_SQL, search));
        }
        if (category != null) {
            queryStringBuffer.append(CATEGORY_SQL + category);
        }
        if (!sort.isEmpty()) {
            if (sort.contains("price")) {
                sort = sort.replace("price", "base_price");
            }
            if (sort.contains("category")) {
                sort = sort.replace("category", "category_id");
            }
            queryStringBuffer.append(ORDER_BY_SQL + sort);
        }
        return queryStringBuffer.toString();
    }
}
