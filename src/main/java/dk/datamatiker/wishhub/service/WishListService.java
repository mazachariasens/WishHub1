package dk.datamatiker.wishhub.service;

import dk.datamatiker.wishhub.model.User;
import dk.datamatiker.wishhub.model.Wish;
import dk.datamatiker.wishhub.model.WishList;
import dk.datamatiker.wishhub.repository.WishlistRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class WishListService {
    WishlistRepo wishlistRepository;
    UserService userService;

    public WishListService(WishlistRepo wishlistRepository, UserService userService){
        this.wishlistRepository = wishlistRepository;
        this.userService = userService;
    }

    // ===== Hent ønskelister for bruger =====
//    public List<WishList> getWishlistsForUser(Long userId) {
//        return wishListRepository.findByUserId(userId);
//    }

    // ===== Opret ønskeliste =====
    public void createWishList(int userId, WishList wishList) {
        User user = userService.findById(userId);
        if (user != null) {
            wishList.setUserId(userId);
            wishlistRepository.save(wishList);
        }
    }

    // ===== Hent ønskeliste =====
    public WishList getWishlistById(int id) {
        return wishlistRepository.findWishlistById(id);
    }

    // ===== Opdater ønskeliste =====
    public boolean updateWishList(int id, WishList updated, int userId) {
        WishList existing = getWishlistById(id);
        if (existing != null && (existing.getUserId() == userId)) {
            existing.setTitle(updated.getTitle());
            existing.setDescription(updated.getDescription());
            wishlistRepository.save(existing);
            return true;
        }
        return false;
    }

    // ===== Slet ønskeliste =====
    public boolean deleteWishList(int id, int userId) {
        WishList existing = getWishlistById(id);
        if (existing != null && (existing.getUserId() == userId)) {
            //wishlistRepository.deleteById(id);
            return true;
        }
        return false;
    }

    // ===== Del ønskeliste =====
    public String generateShareLink(int id, int userId) {
        WishList wishList = getWishlistById(id);
        if (wishList != null && (wishList.getUserId() == userId)) {
            return "https://wishhub.dk/share/" + UUID.randomUUID();
        }
        return null;
    }

    // ===== Ønsker =====
//    public void addWishToList(int wishListId, Wish wish) {
//        WishList wishlist = getWishlistById(wishListId);
//        if (wishlist != null) {
//            wish.setWishList(wishlist);
//            wishRepository.save(wishlist);
//        }
//    }

//    public Wish getWishById(int id) {
//        return wishlistRepository.findWishById(id);
//    }

//    public void updateWish(int id, Wish updated) {
//        Wish existing = getWishById(id);
//        if (existing != null) {
//            existing.setTitle(updated.getTitle());
//            existing.setDescription(updated.getDescription());
//            existing.setLink(updated.getLink());
//            existing.setQuantity(updated.getQuantity());
//            wishRepository.save(existing);
//        }
//    }

//    public void deleteWish(Long id) {
//        if (wishRepository.existsById(id)) {
//            wishRepository.deleteById(id);
//        }
//    }

    public List<WishList> getAll(int id){
        return wishlistRepository.getAll(id);
    }

    public WishList findWishlistByTitle(String title){
        return wishlistRepository.findWishlistByTitle(title);
    }

    public WishList findWishlistById(int id) {
        return wishlistRepository.findWishlistById(id);
    }
}

