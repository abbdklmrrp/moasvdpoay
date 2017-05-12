package jtelecom.dao.entity;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author Moiseienko Petro
 * @since 10.05.2017.
 */
@Service
public class OperationHistoryDaoImpl implements OperationHistoryDao {
    public final static String SELECT_OPERATION_HISTORY_BY_USER="select *\n" +
            "from ( select a.*, rownum rnum\n" +
            "    from ( Select * from OPERATIONS_HISTORY\n" +
            "    WHERE ORDER_ID IN (SELECT ID FROM ORDERS WHERE USER_ID=:userId)\n" +
            "    ORDER BY %s) a\n " +
            "       where rownum <= :length )\n" +
            "       where rnum > :start";

    public final static String SELECT_COUNT_OPERATION_FOR_USER="SELECT COUNT(ID) FROM OPERATIONS_HISTORY " +
            "WHERE ORDER_ID IN (SELECT ID FROM ORDERS WHERE USER_ID=:userId)";

    @Resource
    private NamedParameterJdbcTemplate jdbcTemplate;


    @Override
    public List<OperationHistoryRecord> getOperationHistoryByUserId(Integer userId,int start, int length,String order) {
        MapSqlParameterSource params=new MapSqlParameterSource("userId",userId);
        if(order.isEmpty()){
            order="ID";
        }
        params.addValue("start",start);
        params.addValue("length",length);
        String sql = String.format(SELECT_OPERATION_HISTORY_BY_USER, order);
        return jdbcTemplate.query(sql,params,new OperationHistoryRowMapper());
    }

    @Override
    public Integer getCountOperationForUser(Integer userId) {
        MapSqlParameterSource params=new MapSqlParameterSource("userId",userId);
        return jdbcTemplate.queryForObject(SELECT_COUNT_OPERATION_FOR_USER,params,Integer.class);
    }
}
