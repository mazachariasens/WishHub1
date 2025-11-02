package com.example.wishhubproto.repository;

import com.example.wishhubproto.model.Lists;
import com.example.wishhubproto.model.User;

import com.example.wishhubproto.model.Wish;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.Timestamp;

@org.springframework.stereotype.Repository
public class Repository {

        @Value("${spring.datasource.url}")
        private String dbUrl;

        @Value("${spring.datasource.username}")
        private String username;

        @Value("${spring.datasource.password}")
        private String password;


    private final JdbcTemplate jdbcTemplate;

    public UserRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    //Pay in mind, that the constructors of the model classes are used here, and the order of the arguemnts should match
    //the order in their respectivee constructors.

    //RowMapper - maps a ResultSet to a User object.
    private final RowMapper<User> userRowMapper = (rs, rowNum) -> new User(
            rs.getInt("UserID"),
            rs.getString("UserName"),
            rs.getString("PasswordHash"),
            rs.getString("Email"),
            rs.getTimestamp("CreatedAtTimeOf"));

    //RowMapper - maps a Resultset to a Wish object.
    //ERROR The constructor call doesn't match the constructor on all arguments - lists is missing.
    private final RowMapper<Wish> wishRowMapper = (rs, rowNum) -> new Wish(
            rs.getInt("WishID"),
            rs.getString("WishName"),
            rs.getString("Description"),
            rs.getString("ImgDataPath"));

    private final RowMapper<Lists> listsRowMapper = (rs, rowNum) -> new Lists(
            rs.getString("ListID"),
            rs.getString("ListName"),
            rs.getInt("UserID"));


    //METHOD 1 ______________________________________________________________________________________________________

    //Supports a POST method - a new, populated User object is passed to this method, populated fields from thymeleaf form and populated object from controller method.

    // Method to insert a new user into the database
    //Controller method and service method ought to insert the values by a User object, that they have create into this
    //placeholder.
    public User createUserAndReturn(User user) {
        String sql = "INSERT INTO users (UserName, PasswordHash, Email) VALUES (?, ?, ?)";

        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, user.getUserName());
            ps.setString(2, user.getPasswordHash());
            ps.setString(3, user.getEmail());
            return ps;
        }, keyHolder);
        //keyHolder); is the second argument to the jdbcTemplate.update() method.

        // Get the auto-generated ID from the insert
        int newUserId = keyHolder.getKey().intValue();

        // Returns the full inserte d user (to include timestamp)
        //The returned object can be used in a controller method to confirm if a new user was created +
        //the new user can be displayed.
        return jdbcTemplate.queryForObject("SELECT * FROM users WHERE UserID = ?", userRowMapper, newUserId);
    }

    //Method 2 _______________________________________________________________________________________________________

    // Delete a wish by ID
    //Supports a POST method - Wish id field is passsed to this method.

    //This repository method deletes a Wish object/row from the Wish table in databsae.
    //Due to the design of the databse, any entry with the same ID in the list-relationships table in database will be
    //deleted as well. Because of CASCADE keyword.
    public boolean deleteWishById(int wishId) {
        String sql = "DELETE FROM Wishes WHERE WishID = ?";
        int rowsAffected = jdbcTemplate.update(sql, wishId);
        return rowsAffected > 0;
    }

    //Method 3 _____________________________________________________________________________________________________________


    //Updates a wish
    //Supports a POST method - new populated Wish object is returned to this method, populated fields from thymeleaf form and populated object from controller method.
    public Wish updateWishAndReturn(Wish wish) {
        String sql = "UPDATE Wishes SET Title = ?, Description = ?, ImgDataPath = ? WHERE WishID = ?";
        int rowsAffected = jdbcTemplate.update(sql, wish.getTitle(), wish.getDescription(), wish.getImgDataPath(), wish.getWishID());

        if (rowsAffected > 0) {
            return jdbcTemplate.queryForObject("SELECT * FROM Wishes WHERE WishID = ?", wishRowMapper, wish.getWishID());
        } else {
            return null;
        }
    }

    //Method 4

    //Supports a POST method - Controller method provides the ListID and UserID necessary for recognizing which wish rows and list rows that should be deleted.
    //WishID is not necessary to pass to this method, as ListID is coupled with WishID in the junction table Wish-Relationship.

    //Returns a boolean to confirm to the controller method, whether any rows were affected, i.e. deleted in this case.

    public boolean deleteListAndWishes(int listId, int userId) {
        // Step 1: verify ownership
        Integer ownerId = jdbcTemplate.queryForObject(
                "SELECT UserID FROM Lists WHERE ListID = ?",
                Integer.class,
                listId
        );
        if (ownerId == null || ownerId != userId) {
            return false; // not authorized to delete
        }

        jdbcTemplate.update("DELETE FROM ListsRelationship WHERE ListID = ?", listId);

        jdbcTemplate.update("DELETE FROM Wishes WHERE WishID NOT IN (SELECT WishID FROM ListsRelationship)");

        int rowsAffected = jdbcTemplate.update("DELETE FROM Lists WHERE ListID = ?", listId);

        return rowsAffected > 0;
    }









}
