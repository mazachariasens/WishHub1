package dk.datamatiker.wishhub.repository;

import dk.datamatiker.wishhub.model.WishList;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class WishlistRowMapper implements RowMapper<WishList> {

    @Override
    public WishList mapRow(ResultSet rs, int rowNum) throws SQLException {
        WishList wishlist = new WishList();
        wishlist.setTitle(rs.getString("title"));
        wishlist.setDescription(rs.getString("description"));
        wishlist.setUserID(rs.getInt("user_id"));

        return wishlist;
    }
}
