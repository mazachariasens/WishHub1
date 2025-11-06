package dk.datamatiker.wishhub.repository;

import dk.datamatiker.wishhub.model.Wish;
import dk.datamatiker.wishhub.model.WishList;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class WishRepo {
    JdbcTemplate jdbcTemplate;

    public WishRepo(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Wish> findByWishListId(int wishlistId) {
        String sql = "SELECT * FROM wishes WHERE wishlist_id = ?";
        List<Wish> result = jdbcTemplate.query(sql, new WishRowMapper(), wishlistId);



        return result;
    }
}
