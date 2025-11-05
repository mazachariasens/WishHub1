package dk.datamatiker.wishhub.repository;

import dk.datamatiker.wishhub.model.WishList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WishListRepository extends JpaRepository<WishList, Long> {
    // Hent alle ønskelister for en specifik bruger
    List<WishList> findByUserId(Long userId);
}
