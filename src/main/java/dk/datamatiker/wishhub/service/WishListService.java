package dk.datamatiker.wishhub.service;

import dk.datamatiker.wishhub.model.User;
import dk.datamatiker.wishhub.model.Wish;
import dk.datamatiker.wishhub.model.WishList;
import dk.datamatiker.wishhub.repository.WishListRepository;
import dk.datamatiker.wishhub.repository.WishRepository;
import dk.datamatiker.wishhub.repository.WishlistRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class WishListService {
    WishlistRepo wishlistRepository;

    public WishListService(WishlistRepo wishlistRepository){
        this.wishlistRepository = wishlistRepository;
    }

    @Autowired
    private WishListRepository wishListRepository;

    @Autowired
    private WishRepository wishRepository;

    @Autowired
    private UserService userService;

    // ===== Hent ønskelister for bruger =====
//    public List<WishList> getWishlistsForUser(Long userId) {
//        return wishListRepository.findByUserId(userId);
//    }

    // ===== Opret ønskeliste =====
    public void createWishList(Long userId, WishList wishList) {
        User user = userService.findById(userId);
        if (user != null) {
            wishList.setUser(user);
            wishListRepository.save(wishList);
        }
    }

    // ===== Hent ønskeliste =====
    public WishList getWishListById(Long id) {
        return wishListRepository.findById(id).orElse(null);
    }

    // ===== Opdater ønskeliste =====
    public boolean updateWishList(Long id, WishList updated, Long userId) {
        WishList existing = getWishListById(id);
        if (existing != null && existing.getUser().getId().equals(userId)) {
            existing.setTitle(updated.getTitle());
            existing.setDescription(updated.getDescription());
            wishListRepository.save(existing);
            return true;
        }
        return false;
    }

    // ===== Slet ønskeliste =====
    public boolean deleteWishList(Long id, Long userId) {
        WishList existing = getWishListById(id);
        if (existing != null && existing.getUser().getId().equals(userId)) {
            wishListRepository.deleteById(id);
            return true;
        }
        return false;
    }

    // ===== Del ønskeliste =====
    public String generateShareLink(Long id, Long userId) {
        WishList wishList = getWishListById(id);
        if (wishList != null && wishList.getUser().getId().equals(userId)) {
            return "https://wishhub.dk/share/" + UUID.randomUUID();
        }
        return null;
    }

    // ===== Ønsker =====
    public void addWishToList(Long wishListId, Wish wish) {
        WishList wishList = getWishListById(wishListId);
        if (wishList != null) {
            wish.setWishList(wishList);
            wishRepository.save(wish);
        }
    }

    public Wish getWishById(Long id) {
        return wishRepository.findById(id).orElse(null);
    }

    public void updateWish(Long id, Wish updated) {
        Wish existing = getWishById(id);
        if (existing != null) {
            existing.setTitle(updated.getTitle());
            existing.setDescription(updated.getDescription());
            existing.setLink(updated.getLink());
            existing.setQuantity(updated.getQuantity());
            wishRepository.save(existing);
        }
    }

    public void deleteWish(Long id) {
        if (wishRepository.existsById(id)) {
            wishRepository.deleteById(id);
        }
    }

    public List<WishList> getAll(){
        return wishlistRepository.getAll();
    }

    public void addAttraction(WishList wishlist){
        wishlistRepository.addAttraction(wishlist);
    }

    public WishList findWishlistByTitle(String title){
        return wishlistRepository.findWishlistByTitle(title);
    }
}

