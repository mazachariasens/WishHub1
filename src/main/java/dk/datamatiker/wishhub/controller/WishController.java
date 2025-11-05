package dk.datamatiker.wishhub.controller;

import dk.datamatiker.wishhub.model.Wish;
import dk.datamatiker.wishhub.service.WishService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping("/wishlists/{wishListId}/wishes")
public class WishController {

    @Autowired
    private WishService wishService;

    // ===== Hent alle ønsker for en ønskeliste =====
    @GetMapping
    public String getWishes(@PathVariable Long wishListId,
                            @SessionAttribute("userId") Long userId,
                            Model model,
                            RedirectAttributes redirectAttributes) {
        List<Wish> wishes = wishService.getWishesForWishList(wishListId, userId);
        if (wishes.isEmpty()) {
            redirectAttributes.addFlashAttribute("error", "Ingen ønsker eller adgang nægtet.");
            return "redirect:/wishlists";
        }
        model.addAttribute("wishes", wishes);
        model.addAttribute("wishListId", wishListId);
        model.addAttribute("isLoggedIn", true);
        return "wishlist_details";
    }

    // ===== Opret nyt ønske =====
    @GetMapping("/create")
    public String createWishForm(@PathVariable Long wishListId,
                                 Model model,
                                 HttpSession session) {
        model.addAttribute("wish", new Wish());
        model.addAttribute("wishListId", wishListId);
        model.addAttribute("isLoggedIn", session.getAttribute("userId") != null);
        return "create_wish";
    }

    @PostMapping("/create")
    public String createWish(@PathVariable Long wishListId,
                             @ModelAttribute Wish wish,
                             @SessionAttribute("userId") Long userId,
                             RedirectAttributes redirectAttributes) {
        boolean success = wishService.createWish(wishListId, wish, userId);
        if (!success) {
            redirectAttributes.addFlashAttribute("error", "Kun ejeren kan tilføje ønsker.");
        }
        return "redirect:/wishlists/" + wishListId;
    }

    // ===== Rediger ønske =====
    @GetMapping("/{id}/edit")
    public String editWishForm(@PathVariable Long wishListId,
                               @PathVariable Long id,
                               @SessionAttribute("userId") Long userId,
                               Model model,
                               RedirectAttributes redirectAttributes) {
        Wish wish = wishService.getWishById(id, userId);
        if (wish == null) {
            redirectAttributes.addFlashAttribute("error", "Ønsket findes ikke eller tilhører ikke dig.");
            return "redirect:/wishlists/" + wishListId;
        }
        model.addAttribute("wish", wish);
        model.addAttribute("wishListId", wishListId);
        model.addAttribute("isLoggedIn", true);
        return "edit_wish";
    }

    @PostMapping("/{id}/edit")
    public String editWish(@PathVariable Long wishListId,
                           @PathVariable Long id,
                           @ModelAttribute Wish wish,
                           @SessionAttribute("userId") Long userId,
                           RedirectAttributes redirectAttributes) {
        boolean updated = wishService.updateWish(id, wish, userId);
        if (!updated) {
            redirectAttributes.addFlashAttribute("error", "Kun ejeren kan opdatere ønsket.");
        }
        return "redirect:/wishlists/" + wishListId;
    }

    // ===== Slet ønske =====
    @PostMapping("/{id}/delete")
    public String deleteWish(@PathVariable Long wishListId,
                             @PathVariable Long id,
                             @SessionAttribute("userId") Long userId,
                             RedirectAttributes redirectAttributes) {
        boolean deleted = wishService.deleteWish(id, userId);
        if (!deleted) {
            redirectAttributes.addFlashAttribute("error", "Kun ejeren kan slette ønsket.");
        }
        return "redirect:/wishlists/" + wishListId;
    }
}
