package dk.datamatiker.wishhub.controller;

import dk.datamatiker.wishhub.model.User;
import dk.datamatiker.wishhub.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpSession;

@Controller
public class HomeController {

    @Autowired
    private UserService userService;

    // ===== Hjemmeside =====
    @GetMapping("/")
    public String index(HttpSession session, Model model) {
        model.addAttribute("isLoggedIn", session.getAttribute("userId") != null);
        return "index";
    }   //kig på det gamle undervisningsmateriale for session//

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
            return "redirect:/wishlists";
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
        return "redirect:/login";
    }
}
