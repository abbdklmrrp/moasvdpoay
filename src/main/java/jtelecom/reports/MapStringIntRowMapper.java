package jtelecom.reports;

import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Yuliya Pedash on 01.05.2017.
 */
public class MapStringIntRowMapper implements RowMapper<Map<String, Integer>> {
    @Override
    public Map<String, Integer> mapRow(ResultSet resultSet, int rowNum) throws SQLException {
        Map<String, Integer> map = new HashMap<>();
        while (resultSet.next()) {
            map.put(resultSet.getString("DATE"), resultSet.getInt("COUNT"));
        }
        return map;
    }
}
