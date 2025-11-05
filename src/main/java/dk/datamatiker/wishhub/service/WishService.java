package dk.datamatiker.wishhub.service;

import dk.datamatiker.wishhub.model.User;
import dk.datamatiker.wishhub.model.Wish;
import dk.datamatiker.wishhub.model.WishList;
import dk.datamatiker.wishhub.repository.WishRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WishService {

    @Autowired
    private WishRepository wishRepository;

    @Autowired
    private WishListService wishListService;

    // ===== Hent alle ønsker i en ønskeliste =====
    public List<Wish> getWishesForWishList(Long wishListId, Long userId) {
        WishList wishList = wishListService.getWishListById(wishListId);
        if (wishList != null && wishList.getUser().getId().equals(userId)) {
            return wishRepository.findByWishListId(wishListId);
        }
        return List.of();
    }

    // ===== Opret nyt ønske =====
    public boolean createWish(Long wishListId, Wish wish, Long userId) {
        WishList wishList = wishListService.getWishListById(wishListId);
        if (wishList != null && wishList.getUser().getId().equals(userId)) {
            wish.setWishList(wishList);
            wishRepository.save(wish);
            return true;
        }
        return false;
    }

    // ===== Hent enkelt ønske =====
    public Wish getWishById(Long id, Long userId) {
        Wish wish = wishRepository.findById(id).orElse(null);
        if (wish != null && wish.getWishList().getUser().getId().equals(userId)) {
            return wish;
        }
        return null;
    }

    // ===== Opdater ønske =====
    public boolean updateWish(Long id, Wish updatedWish, Long userId) {
        Wish existing = getWishById(id, userId);
        if (existing != null) {
            existing.setTitle(updatedWish.getTitle());
            existing.setDescription(updatedWish.getDescription());
            existing.setLink(updatedWish.getLink());
            existing.setQuantity(updatedWish.getQuantity());
            wishRepository.save(existing);
            return true;
        }
        return false;
    }

    // ===== Slet ønske =====
    public boolean deleteWish(Long id, Long userId) {
        Wish existing = getWishById(id, userId);
        if (existing != null) {
            wishRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
