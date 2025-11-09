package dk.datamatiker.wishhub.repository;

import dk.datamatiker.wishhub.model.User;
import dk.datamatiker.wishhub.model.WishList;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;

@Repository
public class UserRepo {
    JdbcTemplate jdbcTemplate; // Bruges til at udføre SQL-forespørgsler mod databasen

    public UserRepo(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<WishList> getAll(int id) {
        String sql = "SELECT * FROM wishlists WHERE user_id = ?";
        List<WishList> wishlists = jdbcTemplate.query(sql, new WishlistRowMapper(), id);


        return wishlists; // Returnerer listen med færdige attraction-objekter
    }

    public User findUserByEmail(String email) {
        String sql = "SELECT * FROM users WHERE email = ?";
        List<User> results = jdbcTemplate.query(sql, new UserRowMapper(), email);

        if(results.isEmpty()){
            return null;
        } else {
            return results.get(0);
        }
    }

    public User findUserById(int id) {
        String sql = "SELECT * FROM users WHERE id = ?";
        List<User> results = jdbcTemplate.query(sql, new UserRowMapper(), id);

        User user = results.get(0);

        return user;
    }

    public User save(User user) {
        String sql = "INSERT INTO users (username, email, password) VALUES (?, ?, ?)";

        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, user.getName());
            ps.setString(2, user.getEmail());
            ps.setString(3, user.getPassword());
            return ps;
        }, keyHolder);
        //keyHolder); is the second argument to the jdbcTemplate.update() method.

        // Get the auto-generated ID from the insert
        int newUserId = keyHolder.getKey().intValue();

        // Returns the full inserte d user (to include timestamp)
        //The returned object can be used in a controller method to confirm if a new user was created +
        //the new user can be displayed.
        return jdbcTemplate.queryForObject("SELECT * FROM users WHERE id = ?", new UserRowMapper(), newUserId);
    }
}
