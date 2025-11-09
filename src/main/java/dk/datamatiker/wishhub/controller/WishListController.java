package dk.datamatiker.wishhub.controller;

import dk.datamatiker.wishhub.model.WishList;
import dk.datamatiker.wishhub.service.WishListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.servlet.http.HttpSession;

import java.util.List;

@Controller
@RequestMapping("/wishlists")
public class WishListController {
    private final WishListService wishlistService;

    public WishListController(WishListService wishlistService) {
        this.wishlistService = wishlistService;
    }


    // ===== Hent alle ønskelister for logget bruger =====
    @GetMapping
    public String getAllWishlists(Model model, @SessionAttribute("userId") int userId) {
        List<WishList> wishlists = wishlistService.getAll(userId);
        model.addAttribute("wishlists", wishlists);
        model.addAttribute("isLoggedIn", true);
        return "wishlists";
    }

    // ===== Hent ønskeliste detaljer =====
    @GetMapping("/{id}")
    public String getWishlistDetails(Model model, @SessionAttribute("userId") int userId, @PathVariable int id) {

        WishList wishList = wishlistService.findWishlistById(id);
        if (wishList == null || !(wishList.getUserId() == userId)) {
            return "redirect:/wishlists";
        }
        model.addAttribute("wishlist", wishList);
        model.addAttribute("isLoggedIn", true);
        return "wishlist_details";
    }

    // ===== Opret ny ønskeliste =====
    @GetMapping("/create")
    public String createWishlistForm(Model model, HttpSession session) {
        model.addAttribute("wishlist", new WishList());
        model.addAttribute("isLoggedIn", session.getAttribute("userId") != null);
        return "create_wishlist";
    }

    @PostMapping("/create")
    public String createWishlist(@ModelAttribute WishList wishlist,
                                 @SessionAttribute("userId") int userId) {
        wishlistService.createWishList(userId, wishlist);
        return "redirect:/wishlists";
    }

    // ===== Rediger ønskeliste =====
    @GetMapping("/{id}/edit")
    public String editWishlistForm(@PathVariable int id, Model model,
                                   HttpSession session,
                                   @SessionAttribute("userId") Long userId,
                                   RedirectAttributes redirectAttributes) {
        WishList wishlist = wishlistService.getWishlistById(id);
        if (wishlist == null || !(wishlist.getUserId() == userId)) {
            redirectAttributes.addFlashAttribute("error", "Ønskelisten findes ikke eller tilhører ikke dig.");
            return "redirect:/wishlists";
        }
        model.addAttribute("wishlist", wishlist);
        model.addAttribute("isLoggedIn", true);
        return "edit_wishlist";
    }

    @PostMapping("/{id}/edit")
    public String editWishlist(@PathVariable int id,
                               @ModelAttribute WishList wishlist,
                               @SessionAttribute("userId") int userId,
                               RedirectAttributes redirectAttributes) {
        boolean updated = wishlistService.updateWishList(id, wishlist, userId);
        if (!updated) {
            redirectAttributes.addFlashAttribute("error", "Kun ejeren kan opdatere ønskelisten.");
        }
        return "redirect:/wishlists/" + id;
    }

    // ===== Slet ønskeliste =====
    @PostMapping("/{id}/delete")
    public String deleteWishlist(@PathVariable int id,
                                 @SessionAttribute("userId") int userId,
                                 RedirectAttributes redirectAttributes) {
        boolean deleted = wishlistService.deleteWishList(id, userId);
        if (!deleted) {
            redirectAttributes.addFlashAttribute("error", "Kun ejeren kan slette ønskelisten.");
        }
        return "redirect:/wishlists";
    }

    // ===== Del ønskeliste =====
    @GetMapping("/{id}/share")
    public String shareWishlist(@PathVariable int id,
                                @SessionAttribute("userId") int userId,
                                RedirectAttributes redirectAttributes) {
        String link = wishlistService.generateShareLink(id, userId);
        if (link == null) {
            redirectAttributes.addFlashAttribute("error", "Kun ejeren kan dele ønskelisten.");
            return "redirect:/wishlists";
        }
        redirectAttributes.addFlashAttribute("shareLink", link);
        return "redirect:/wishlists/" + id;
    }
}
