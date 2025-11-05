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

    @Autowired
    private WishListService wishListService;

    // ===== Hent alle ønskelister for logget bruger =====
    @GetMapping
    public String getAllWishlists(Model model, HttpSession session, @SessionAttribute("userId") Long userId) {
        List<WishList> wishlists = wishListService.getWishlistsForUser(userId);
        model.addAttribute("wishlists", wishlists);
        model.addAttribute("isLoggedIn", true);
        return "wishlists";
    }

    // ===== Hent ønskeliste detaljer =====
    @GetMapping("/{id}")
    public String getWishlistDetails(@PathVariable Long id, Model model,
                                     HttpSession session,
                                     @SessionAttribute("userId") Long userId,
                                     RedirectAttributes redirectAttributes) {
        WishList wishList = wishListService.getWishListById(id);
        if (wishList == null || !wishList.getUser().getId().equals(userId)) {
            redirectAttributes.addFlashAttribute("error", "Ønskelisten findes ikke eller tilhører ikke dig.");
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
                                 @SessionAttribute("userId") Long userId) {
        wishListService.createWishList(userId, wishlist);
        return "redirect:/wishlists";
    }

    // ===== Rediger ønskeliste =====
    @GetMapping("/{id}/edit")
    public String editWishlistForm(@PathVariable Long id, Model model,
                                   HttpSession session,
                                   @SessionAttribute("userId") Long userId,
                                   RedirectAttributes redirectAttributes) {
        WishList wishlist = wishListService.getWishListById(id);
        if (wishlist == null || !wishlist.getUser().getId().equals(userId)) {
            redirectAttributes.addFlashAttribute("error", "Ønskelisten findes ikke eller tilhører ikke dig.");
            return "redirect:/wishlists";
        }
        model.addAttribute("wishlist", wishlist);
        model.addAttribute("isLoggedIn", true);
        return "edit_wishlist";
    }

    @PostMapping("/{id}/edit")
    public String editWishlist(@PathVariable Long id,
                               @ModelAttribute WishList wishlist,
                               @SessionAttribute("userId") Long userId,
                               RedirectAttributes redirectAttributes) {
        boolean updated = wishListService.updateWishList(id, wishlist, userId);
        if (!updated) {
            redirectAttributes.addFlashAttribute("error", "Kun ejeren kan opdatere ønskelisten.");
        }
        return "redirect:/wishlists/" + id;
    }

    // ===== Slet ønskeliste =====
    @PostMapping("/{id}/delete")
    public String deleteWishlist(@PathVariable Long id,
                                 @SessionAttribute("userId") Long userId,
                                 RedirectAttributes redirectAttributes) {
        boolean deleted = wishListService.deleteWishList(id, userId);
        if (!deleted) {
            redirectAttributes.addFlashAttribute("error", "Kun ejeren kan slette ønskelisten.");
        }
        return "redirect:/wishlists";
    }

    // ===== Del ønskeliste =====
    @GetMapping("/{id}/share")
    public String shareWishlist(@PathVariable Long id,
                                @SessionAttribute("userId") Long userId,
                                RedirectAttributes redirectAttributes) {
        String link = wishListService.generateShareLink(id, userId);
        if (link == null) {
            redirectAttributes.addFlashAttribute("error", "Kun ejeren kan dele ønskelisten.");
            return "redirect:/wishlists";
        }
        redirectAttributes.addFlashAttribute("shareLink", link);
        return "redirect:/wishlists/" + id;
    }
}
