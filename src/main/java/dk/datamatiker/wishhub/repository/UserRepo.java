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

        makeTables();
        populateTables();
    }

    public void makeTables(){
        jdbcTemplate.execute("create table if not exists users (id int auto_increment primary key, name varchar(255), email varchar(255), password varchar(255))");
        jdbcTemplate.execute("create table if not exists wishlists (id int auto_increment primary key, title varchar(255), description varchar(255), user_id int)");
        jdbcTemplate.execute("create table if not exists wishes (id int auto_increment primary key, description varchar(255), wishlist_id int, quantity int, title varchar(255))");
    }

    public void populateTables(){
        jdbcTemplate.update("insert ignore into users (email, name, password) values(?,?,?)", "user@mail.dk", "user", "1234");
        jdbcTemplate.update("insert ignore into wishlists (description, title, user_id) values(?,?,?)", "min ønskeliste", "ønskeliste nr. 1", 1);
        jdbcTemplate.update("insert ignore into wishes (description, wishlist_id, quantity, title) values(?,?,?,?)", "Spille konsol", 1, 1, "PS6");
    }

    public List<WishList> getAll(int id) {
        String sql = "SELECT * FROM wishlists WHERE user_id = ?";
        List<WishList> wishlists = jdbcTemplate.query(sql, new WishlistRowMapper(), id);


        return wishlists; // Returnerer listen med færdige attraction-objekter
    }


    public void addAttraction(WishList wishlist) {
        String sql = "INSERT INTO wishlists (title, description, user_id) VALUES (?, ?, ?)";
        jdbcTemplate.update(sql, wishlist.getTitle(), wishlist.getDescription(), wishlist.getUserId());
    }

    public void deleteAttraction(String name) {
        // Finder attractionID ud fra navn
        String findIdSql = "SELECT id FROM attractions WHERE name = ?";
        Integer attractionId = jdbcTemplate.queryForObject(findIdSql, Integer.class, name);

        // Slet først alle attractionTags, der er knyttet til attractionId
        String deleteTagsSql = "DELETE FROM attractionTags WHERE attractionID = ?";
        jdbcTemplate.update(deleteTagsSql, attractionId);

        // Sletter selve attraction fra attractions-tabellen
        String sql = "DELETE FROM attractions WHERE name = ?";
        jdbcTemplate.update(sql, name);
    }

    public User findUserByEmail(String email) {
        String sql = "SELECT * FROM users WHERE email = ?";
        List<User> results = jdbcTemplate.query(sql, new UserRowMapper(), email);

        User user = results.get(0);

        return user;
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
