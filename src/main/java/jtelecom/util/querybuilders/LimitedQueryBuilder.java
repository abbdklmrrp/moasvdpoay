package jtelecom.util.querybuilders;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;

/**
 * Created by Yuliya Pedash on 15.05.2017.
 */
public class LimitedQueryBuilder {
    private static final String SEARCH_SQL = " AND LOWER(name) LIKE LOWER('%s') || '%%' ";
    private static final String CATEGORY_SQL = " AND category_id = ";
    private static final String ORDER_BY_SQL = " ORDER BY ";
    private static MapSqlParameterSource params = new MapSqlParameterSource();
    private static final char OPEN_PARENTHESIS = '(';
    private static final char CLOSED_PARENTHESIS = ')';
    private static final String SORT_QUERY_BEGINS = "OVER(";

    public static String getQuery(String query, String sort, String search, Integer category) {
        String subQuery = query.substring(query.indexOf(OPEN_PARENTHESIS) + 1, query.lastIndexOf(CLOSED_PARENTHESIS));
        StringBuffer subQueryStringBuffer = new StringBuffer(subQuery);
        if (!search.isEmpty()) {
            subQueryStringBuffer.append(String.format(SEARCH_SQL, search));
        }
        if (category != null) {
            subQueryStringBuffer.append(CATEGORY_SQL + category);
        }
        if (!sort.isEmpty()) {
            Integer sortQueryBeginsIndex = subQueryStringBuffer.lastIndexOf(String.valueOf(SORT_QUERY_BEGINS));
            Integer sortQueryEndsIndex = subQueryStringBuffer.indexOf(String.valueOf(CLOSED_PARENTHESIS), sortQueryBeginsIndex);
            //           String sortQuery = subQuery.substring(sortQueryBeginsIndex  + SORT_QUERY_BEGINS.length(), sortQueryEndsIndex);
            if (sort.contains("price")) {
                sort = sort.replace("price", "base_price");
            }
            if (sort.contains("category")) {
                sort = sort.replace("category", "category_id");
            }
            if (sort.contains("product_name")) {
                sort = sort.replace("product_name", "name");
            }
            subQueryStringBuffer.replace(sortQueryBeginsIndex + SORT_QUERY_BEGINS.length(), sortQueryEndsIndex, ORDER_BY_SQL + sort);
        }
        return query.replace(subQuery, subQueryStringBuffer);
    }
}
