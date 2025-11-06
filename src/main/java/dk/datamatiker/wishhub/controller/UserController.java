package dk.datamatiker.wishhub.controller;

import dk.datamatiker.wishhub.model.User;
import dk.datamatiker.wishhub.model.WishList;
import dk.datamatiker.wishhub.service.UserService;
import dk.datamatiker.wishhub.service.WishListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private WishListService wishListService;

    // ===== Hent logget bruger og ønskelister =====
    @GetMapping("/profile")
    public String userProfile(HttpSession session, Model model) {
        Integer userId = (Integer) session.getAttribute("userId");
        if (userId == null) {
            return "redirect:/login"; // hvis ikke logget ind → login
        }

        User user = userService.findById(userId);
        model.addAttribute("user", user);

        List<WishList> wishlists = wishListService.getAll(userId);
        model.addAttribute("wishlists", wishlists);

        model.addAttribute("isLoggedIn", true); // allerede logget ind
        return "wishlists";
    }
}
