package com.example.wishhubproto.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

//GENERAL NOTES:

//The use of just private boolean isLoggedIn = false;, will create errors, as Spring controller class is a SINGLETON, which
//means that only one instance is used for all users? When a user logs in, all users get logged in.
//SpringSecurity and HttpSession class can be used here.


@Controller
public class WishListController {

    // Midlertidig login-status (normalt ville man bruge sessions eller Spring Security)
    private boolean isLoggedIn = false;

    // ===== Forside =====
    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("isLoggedIn", isLoggedIn);
        return "index";
    }


    // ===== Login side (GET) =====
    @GetMapping("/login")
    public String loginPage(Model model) {
        model.addAttribute("isLoggedIn", false);
        return "login";
    }

    // ===== Login handling (POST) =====
    @PostMapping("/login")
    public String loginSubmit(@RequestParam String email,
                              @RequestParam String password,
                              Model model) {


        // Dummy login — senere erstattes med database-validering
        if (email.equals("test@wishhub.dk") && password.equals("1234")) {
            isLoggedIn = true;
            return "redirect:/wishlists";
        } else {
            model.addAttribute("error", "Forkert email eller password");
            model.addAttribute("isLoggedIn", false);
            return "login";
        }
    }

    // ===== Log ud =====
    @GetMapping("/logout")
    public String logout() {
        isLoggedIn = false;
        return "redirect:/";
    }

    // ===== Ønskelister (kræver login) =====
    @GetMapping("/wishlists")
    public String wishlists(Model model) {
        model.addAttribute("isLoggedIn", isLoggedIn);

        if (!isLoggedIn) {
            // Hvis ikke logget ind → tilbage til login
            return "redirect:/login";
        }

        return "wishlists";
    }

    // ===== Registrer side =====
    @GetMapping("/registrer")
    public String registrer(Model model) {
        model.addAttribute("isLoggedIn", false);
        return "registrer";
    }
}
