package dk.datamatiker.wishhub.repository;

import dk.datamatiker.wishhub.model.User;
import dk.datamatiker.wishhub.model.WishList;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

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

    public void save(User user) {
        String sql = "INSERT INTO users (name, email, password) VALUES (?, ?, ?)";
        jdbcTemplate.update(sql, user.getName(), user.getEmail(), user.getPassword());
    }
}
