package com.gm2.cryptoapp.repository;

import com.gm2.cryptoapp.entity.Coin;
import org.springframework.jdbc.core.JdbcTemplate;

public class CoinRepository {

    private static String INSERT = "insert into coin (name,price,datetime,quantity) values(?,?,?,?)";
    private JdbcTemplate jdbcTemplate;

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

}
