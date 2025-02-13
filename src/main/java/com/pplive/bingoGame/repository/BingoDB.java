package com.pplive.bingoGame.repository;


import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;

@Repository
public class BingoDB {

    private final JdbcTemplate jdbcTemplate;

    public BingoDB(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }

    public double findBalanceByUserId(int userId) {
        String query = "SELECT balance FROM user_details WHERE user_id = ?";
        try {
            RowMapper<Double> rowMapper = (resultSet, rowNum) -> resultSet.getDouble("balance");
            return jdbcTemplate.queryForObject(query, rowMapper, userId);
        } catch (Exception e) {
            // Handle exceptions, maybe return a default value or throw a custom exception
            throw new RuntimeException("User not found or error fetching balance", e);
        }
    }




}
