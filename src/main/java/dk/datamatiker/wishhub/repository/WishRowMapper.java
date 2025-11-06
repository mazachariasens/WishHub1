package dk.datamatiker.wishhub.repository;

import dk.datamatiker.wishhub.model.Wish;
import dk.datamatiker.wishhub.model.WishList;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class WishRowMapper implements RowMapper<Wish> {

    @Override
    public Wish mapRow(ResultSet rs, int rowNum) throws SQLException {
        Wish wish = new Wish();
        wish.setId(rs.getInt("id"));
        wish.setTitle(rs.getString("title"));
        wish.setDescription(rs.getString("description"));
        wish.setQuantity(rs.getInt("quantity"));
        wish.setWishlistId(rs.getInt("wishlist_id"));

        return wish;
    }
}
