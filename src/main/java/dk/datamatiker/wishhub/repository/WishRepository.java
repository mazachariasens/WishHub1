package dk.datamatiker.wishhub.repository;

import dk.datamatiker.wishhub.model.Wish;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WishRepository extends JpaRepository<Wish, Long> {
    // Hent alle ønsker for en specifik ønskeliste
    List<Wish> findByWishListId(Long wishListId);
}
