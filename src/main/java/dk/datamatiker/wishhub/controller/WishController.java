package dk.datamatiker.wishhub.controller;

import dk.datamatiker.wishhub.model.Wish;
import dk.datamatiker.wishhub.model.WishList;
import dk.datamatiker.wishhub.service.WishListService;
import dk.datamatiker.wishhub.service.WishService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping("/wishlists/{id}/wishes")
public class WishController {
    WishService wishService;
    WishListService wishlistService;

    public WishController(WishService wishService, WishListService wishlistService){
        this.wishService = wishService;
        this.wishlistService = wishlistService;
    }

//    // ===== Hent alle ønsker for en ønskeliste =====
    @GetMapping
    public String getWishes(@PathVariable int id,
                            @SessionAttribute("userId") int userId,
                            Model model,
                            RedirectAttributes redirectAttributes) {
        System.out.println("hello");
        List<Wish> wishes = wishService.getWishesForWishList(id, userId);
        System.out.println(wishes);
        if (wishes.isEmpty()) {
            redirectAttributes.addFlashAttribute("error", "Ingen ønsker eller adgang nægtet.");
            return "redirect:/wishlists";
        }
        System.out.println("xxx-----------xxxxxsada");
        WishList wishlist = wishlistService.getWishlistById(id);
        System.out.println(wishlist);
        model.addAttribute("wishes", wishes);
        model.addAttribute("wishListId", id);
        model.addAttribute("wishlist", wishlist);
        model.addAttribute("isLoggedIn", true);
        return "wishlist_details";
    }
//
//    // ===== Opret nyt ønske =====
//    @GetMapping("/create")
//    public String createWishForm(@PathVariable Long wishListId,
//                                 Model model,
//                                 HttpSession session) {
//        model.addAttribute("wish", new Wish());
//        model.addAttribute("wishListId", wishListId);
//        model.addAttribute("isLoggedIn", session.getAttribute("userId") != null);
//        return "create_wish";
//    }
//
//    @PostMapping("/create")
//    public String createWish(@PathVariable int wishListId,
//                             @ModelAttribute Wish wish,
//                             @SessionAttribute("userId") int userId,
//                             RedirectAttributes redirectAttributes) {
//        boolean success = wishService.createWish(wishListId, wish, userId);
//        if (!success) {
//            redirectAttributes.addFlashAttribute("error", "Kun ejeren kan tilføje ønsker.");
//        }
//        return "redirect:/wishlists/" + wishListId;
//    }
//
//    // ===== Rediger ønske =====
//    @GetMapping("/{id}/edit")
//    public String editWishForm(@PathVariable int wishListId,
//                               @PathVariable int id,
//                               @SessionAttribute("userId") int userId,
//                               Model model,
//                               RedirectAttributes redirectAttributes) {
//        Wish wish = wishService.getWishById(id, userId);
//        if (wish == null) {
//            redirectAttributes.addFlashAttribute("error", "Ønsket findes ikke eller tilhører ikke dig.");
//            return "redirect:/wishlists/" + wishListId;
//        }
//        model.addAttribute("wish", wish);
//        model.addAttribute("wishListId", wishListId);
//        model.addAttribute("isLoggedIn", true);
//        return "edit_wish";
//    }
//
//    @PostMapping("/{id}/edit")
//    public String editWish(@PathVariable Long wishListId,
//                           @PathVariable Long id,
//                           @ModelAttribute Wish wish,
//                           @SessionAttribute("userId") Long userId,
//                           RedirectAttributes redirectAttributes) {
//        boolean updated = wishService.updateWish(id, wish, userId);
//        if (!updated) {
//            redirectAttributes.addFlashAttribute("error", "Kun ejeren kan opdatere ønsket.");
//        }
//        return "redirect:/wishlists/" + wishListId;
//    }
//
//    // ===== Slet ønske =====
//    @PostMapping("/{id}/delete")
//    public String deleteWish(@PathVariable Long wishListId,
//                             @PathVariable Long id,
//                             @SessionAttribute("userId") Long userId,
//                             RedirectAttributes redirectAttributes) {
//        boolean deleted = wishService.deleteWish(id, userId);
//        if (!deleted) {
//            redirectAttributes.addFlashAttribute("error", "Kun ejeren kan slette ønsket.");
//        }
//        return "redirect:/wishlists/" + wishListId;
//    }
}
