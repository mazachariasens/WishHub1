package dk.datamatiker.wishhub.repository;

import dk.datamatiker.wishhub.model.User;
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
        String sql = "INSERT INTO wishlists (title, description, userId) VALUES (?, ?, ?)";
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

    public void updateAttraction(WishList updatedAttraction) {
//        // Finder en attraction ud fra navn
//        String sql = "UPDATE attractions SET name = ?, description = ?, citiesID = ? WHERE id = ?";
//        jdbcTemplate.update(sql,
//                updatedAttraction.getName(),
//                updatedAttraction.getDescription(),
//                updatedAttraction.getCity().getId(),
//                updatedAttraction.getId());
//// Sletter alle eksisterende tag-koblinger for at erstatte dem
//        String deleteSql = "DELETE FROM attractionTags WHERE attractionID = ?";
//        jdbcTemplate.update(deleteSql, updatedAttraction.getId());
//
//        //Tjekker om man har valgt tags - for hvert tag laves der en række i attractionTags
//        if (tags != null && !tags.isEmpty()) {
//            String sqlTag = "INSERT INTO attractionTags (attractionID, tagID) VALUES (?, ?)";
//            for (Tags tag : tags) {
//                jdbcTemplate.update(sqlTag, updatedAttraction.getId(), tag.getId());
//            }
//        }

    }
}
