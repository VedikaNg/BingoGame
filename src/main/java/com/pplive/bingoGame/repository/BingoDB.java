package com.pplive.bingoGame.repository;


import com.pplive.bingoGame.dto.BetDetails;
import com.pplive.bingoGame.service.BingoGameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;


@Repository
public class BingoDB {

    @Autowired
    BingoGameService bingoGameService;

    private final JdbcTemplate jdbcTemplate;

    public BingoDB(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }

    public int findBalanceByUserId(int userId) {
        String query = "SELECT balance FROM user_details WHERE user_id = ?";
        try {
            RowMapper<Integer> rowMapper = (resultSet, rowNum) -> resultSet.getInt("balance");
            return jdbcTemplate.queryForObject(query, rowMapper, userId);
        } catch (Exception e) {
            throw new RuntimeException("User not found or error fetching balance", e);
        }
    }



    public BetDetails findUserIdBetAmountBetCodeByGameId(String gameId){
        String query = "SELECT user_id, bet_amount, bet_code FROM bet_details WHERE game_id = ? ";
        try {
            RowMapper<BetDetails> rowMapper = (resultSet, rowNum) -> new BetDetails(
                    resultSet.getInt("user_id"), resultSet.getInt("bet_amount"),
                    resultSet.getInt("bet_code"));
            return jdbcTemplate.queryForObject(query, rowMapper, gameId);
        } catch (Exception e) {
            throw new RuntimeException("User not found or error fetching balance", e);
        }
    }

    public void updateBalanceInDB(int balance, int userId){
        String query = "UPDATE user_details SET balance = ? WHERE user_id = ? ";
        jdbcTemplate.update(query,balance,userId);
    }

    public void insertIntoBetTable(String betCode, int betAmount, int userId, String gameId){
        String query = "INSERT INTO bet_details(bet_id, bet_code, bet_amount, user_id, game_id) VALUES (?,?,?,?,?)";
        jdbcTemplate.update(query,bingoGameService.generateBetId(userId,betCode) ,
                bingoGameService.betCodeMapping(betCode),betAmount,userId,gameId);
    }

    public void insertBingoGameIdIntoDB(String gameId){
        String query = "INSERT INTO game_details(game_id, game_type) VALUES (?,?)";
        jdbcTemplate.update(query, gameId, "BINGO");
    }

    public void insertIntoTransactionTable(int userId, String gameId,
                                           String transactionType, int amount, LocalDateTime dateTime, String status){
        String query = "INSERT INTO transaction(user_id, game_id, transaction_type," +
                " amount, transaction_date, status) VALUES (?,?,?,?,?,?)";
        jdbcTemplate.update(query,userId,gameId,transactionType,amount,dateTime,status);
    }



}
