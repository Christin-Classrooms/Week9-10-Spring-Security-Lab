package com.example.Thymeleaf.Demo.repository;


import com.example.Thymeleaf.Demo.Model.Player;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class PlayerRepo {


    private final JdbcTemplate jdbcTemplate;


    public PlayerRepo(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void save(Player player){

        String sql = "insert into players (name, email) values(?,?)";
        jdbcTemplate.update(sql, player.getName(),player.getEmail());


    }


    public List<Player> findAll(){


        String sql = "Select * from players";

        RowMapper<Player> mapper = new RowMapper<Player>() {
            @Override
            public Player mapRow(ResultSet rs, int rowNum) throws SQLException {

                Player player = new Player();
                player.setId(rs.getInt("id"));
                player.setName(rs.getString("name"));
                player.setEmail(rs.getString("email"));

                return player;
            }
        };


        return jdbcTemplate.query(sql, mapper);


    }


    public Player findById(int id){

        String sql = "Select * from players where id = ?";

        RowMapper<Player> mapper = new RowMapper<Player>() {
            @Override
            public Player mapRow(ResultSet rs, int rowNum) throws SQLException {

                Player player = new Player();

                player.setId(rs.getInt("id"));
                player.setName(rs.getString("name"));
                player.setEmail(rs.getString("email"));

                return player;
            }
        };

        List<Player> results = jdbcTemplate.query(sql, mapper, id);

        return results.isEmpty() ? null : results.get(0);

    }

}