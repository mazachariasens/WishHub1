package dk.datamatiker.wishhub.repository;

import dk.datamatiker.wishhub.model.WishList;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class WishlistRepo {
    JdbcTemplate jdbcTemplate; // Bruges til at udføre SQL-forespørgsler mod databasen

    public WishlistRepo(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<WishList> getAll(int id) {
        String sql = "SELECT * FROM wishlists WHERE user_id = ?";
        List<WishList> wishlists = jdbcTemplate.query(sql, new WishlistRowMapper(), id);


        return wishlists; // Returnerer listen med færdige attraction-objekter
    }


    public void save(WishList wishlist) {
        String sql = "INSERT INTO wishlists (title, description, user_id) VALUES (?, ?, ?)";
        jdbcTemplate.update(sql, wishlist.getTitle(), wishlist.getDescription(), wishlist.getUserId());
    }

    public WishList findWishlistByTitle(String title) {
        String sql = "SELECT * FROM wishlists WHERE title = ?";
        List<WishList> results = jdbcTemplate.query(sql, new WishlistRowMapper(), title);

        WishList wishlist = results.get(0);

        return wishlist;
    }

    public WishList findWishlistById(int id) {
        String sql = "SELECT * FROM wishlists WHERE id = ?";
        List<WishList> results = jdbcTemplate.query(sql, new WishlistRowMapper(), id);

        WishList wishlist = results.get(0);

        return wishlist;
    }
}
