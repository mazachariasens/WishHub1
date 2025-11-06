package dk.datamatiker.wishhub.service;

import dk.datamatiker.wishhub.model.Wish;
import dk.datamatiker.wishhub.model.WishList;
import dk.datamatiker.wishhub.repository.WishRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WishService {
    WishListService wishlistService;
    WishRepo wishRepository;

    public WishService(WishListService wishlistService, WishRepo wishRepository){
        this.wishlistService = wishlistService;
        this.wishRepository = wishRepository;
    }

    // ===== Hent alle ønsker i en ønskeliste =====
    public List<Wish> getWishesForWishList(int wishListId, int userId) {
        WishList wishList = wishlistService.getWishlistById(wishListId);
        if (wishList != null && (wishList.getUserId() == userId)) {
            return wishRepository.findByWishListId(wishListId);
        }
        return List.of();
    }
//
//    // ===== Opret nyt ønske =====
//    public boolean createWish(int wishListId, Wish wish, int userId) {
//        wishRepository
//        return false;
//    }
//
//    // ===== Hent enkelt ønske =====
//    public Wish getWishById(int id, int userId) {
//        Wish wish = wishRepository.findWishById(id);
//        if (wish != null && (wish.getWishList().getUserId() == userId)) {
//            return wish;
//        }
//        return null;
//    }
//
//    // ===== Opdater ønske =====
//    public boolean updateWish(Long id, Wish updatedWish, Long userId) {
//        Wish existing = getWishById(id, userId);
//        if (existing != null) {
//            existing.setTitle(updatedWish.getTitle());
//            existing.setDescription(updatedWish.getDescription());
//            existing.setLink(updatedWish.getLink());
//            existing.setQuantity(updatedWish.getQuantity());
//            wishRepository.save(existing);
//            return true;
//        }
//        return false;
//    }
//
//    // ===== Slet ønske =====
//    public boolean deleteWish(Long id, Long userId) {
//        Wish existing = getWishById(id, userId);
//        if (existing != null) {
//            wishRepository.deleteById(id);
//            return true;
//        }
//        return false;
//    }
}
