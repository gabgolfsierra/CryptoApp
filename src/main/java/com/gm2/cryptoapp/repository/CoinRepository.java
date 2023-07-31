package com.gm2.cryptoapp.repository;

import com.gm2.cryptoapp.dto.CoinDTO;
import com.gm2.cryptoapp.entity.Coin;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
@EnableAutoConfiguration
public class CoinRepository {

    private final static String INSERT = "INSERT INTO coin (name,price,datetime,quantity) VALUES(?,?,?,?)";

    private final static String SELECT_ALL = "SELECT name, sum(quantity) AS quantity FROM coin GROUP BY name";

    private final static String SELECT_BY_NAME = "SELECT * FROM coin WHERE name = ?";

    private final static String DELETE = "DELETE FROM coin WHERE id = ?";

    private final JdbcTemplate jdbcTemplate;

    public CoinRepository(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }

    public Coin insert (Coin coin){
        Object[] attr = new Object[]{
                coin.getName(),
                coin.getPrice(),
                coin.getDateTime(),
                coin.getQuantity()
        };
        jdbcTemplate.update(INSERT,attr);
        return coin;
    }

    public List<CoinDTO> getAll(){
        return jdbcTemplate.query(SELECT_ALL, new RowMapper<CoinDTO>(){
            @Override
            public CoinDTO mapRow(ResultSet rs, int rowNum) throws SQLException{
                CoinDTO coin = new CoinDTO();
                coin.setName(rs.getString("name"));
                coin.setQuantity(rs.getBigDecimal("quantity"));

                return coin;
            }
        });
    }

    public List<Coin> getByName(String name){
        Object[] attr = new Object[] { name };
        return jdbcTemplate.query(SELECT_BY_NAME, new RowMapper<Coin>() {
            @Override
            public Coin mapRow(ResultSet rs, int rowNum) throws SQLException {
                Coin coin = new Coin();
                coin.setId(rs.getInt("id"));
                coin.setName(rs.getString("name"));
                coin.setPrice(rs.getBigDecimal("price"));
                coin.setQuantity(rs.getBigDecimal("quantity"));
                coin.setDateTime(rs.getTimestamp("datetime"));

                return coin;

            }
        });
    }

    public int remove(int id){
        return jdbcTemplate.update(DELETE, id);
    }

}
