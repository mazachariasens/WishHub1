package dk.datamatiker.wishhub.controller;

import dk.datamatiker.wishhub.model.User;
import dk.datamatiker.wishhub.model.WishList;
import dk.datamatiker.wishhub.service.UserService;
import dk.datamatiker.wishhub.service.WishListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.servlet.http.HttpSession;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/user")
public class UserController {

    private final UserService userService;
    private final WishListService wishListService;

    public UserController(UserService userService, WishListService wishListService) {
        this.userService = userService;
        this.wishListService = wishListService;
    }

    // ===== Registreringsformular =====
    @GetMapping("/registrer")
    public String showRegisterForm(HttpSession session, Model model) {
        model.addAttribute("isLoggedIn", session.getAttribute("userId") != null);
        return "registrer";
    } //der skal være en model, ikke session//

    @PostMapping("/registrer")
    public String registerUser(@RequestParam String name,
                               @RequestParam String email,
                               @RequestParam String password,
                               Model model,
                               HttpSession session) {
        try {
            User user = userService.opretBruger(name, email, password);
            session.setAttribute("userId", user.getId()); // log automatisk ind
            return "redirect:/wishlists";
        } catch (IllegalArgumentException e) {
            model.addAttribute("error", e.getMessage());
            model.addAttribute("isLoggedIn", session.getAttribute("userId") != null);
            return "registrer";
        }
    }

    // ===== Loginformular =====
    @GetMapping("/login")
    public String showLoginForm(HttpSession session, Model model) {
        model.addAttribute("isLoggedIn", session.getAttribute("userId") != null);
        return "login";
    }
    // Først her oprettees en session
    @PostMapping("/login")
    public String loginUser(@RequestParam String email,
                            @RequestParam String password,
                            HttpSession session,
                            Model model) {

        User user = userService.login(email, password);
        if (user != null) {
            session.setAttribute("userId", user.getId());
            return "redirect:/";
        } else {
            model.addAttribute("error", "Forkert email eller adgangskode.");
            model.addAttribute("isLoggedIn", session.getAttribute("userId") != null);
            return "login";
        }
    }

    // ===== Logout =====
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/user/login";
    }

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
