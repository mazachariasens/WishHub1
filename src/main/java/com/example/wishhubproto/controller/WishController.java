package com.example.wishhubproto.controller;

import com.example.wishhubproto.model.Lists;
import com.example.wishhubproto.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.example.wishhubproto.service.Service;

import java.util.List;

//GENERAL NOTES:

//The use of just private boolean isLoggedIn = false;, will create errors, as Spring controller class is a SINGLETON, which
//means that only one instance is used for all users? When a user logs in, all users get logged in.
//SpringSecurity and HttpSession class can be used here.


@Controller
public class WishController {
    private final Service service;

    @Autowired
    public WishController(Service service) {
        this.service = service;
    }

    // Midlertidig login-status (normalt ville man bruge sessions eller Spring Security)
    private boolean isLoggedIn = false;


    @RequestMapping("/")
    public String index(Model model) {
        model.addAttribute("isLoggedIn", isLoggedIn);

        if (isLoggedIn) {
            int userID = 1;
            List<Lists> userLists = service.getAllListsByUser(userID);
            model.addAttribute("userLists", userLists);
        }

        return "index";
    }

    @GetMapping("/login")
    public String loginPage(Model model) {
        User user = new User();
        model.addAttribute("user", user);
        model.addAttribute("isLoggedIn", false);
        return "login";
    }

    @PostMapping("/authenticateUser")
    public String authenticateUser(@ModelAttribute User user, Model model) {

        User authenticatedUser = service.authenticateUser(user);

        if (authenticatedUser != null) {
            isLoggedIn = true; //
            model.addAttribute("isLoggedIn", true);
            model.addAttribute("userLists", service.getAllListsByUser(authenticatedUser.getUserID()));
            return "index"; // show home page with wishlists
        } else {
            isLoggedIn = false;
            model.addAttribute("isLoggedIn", false);
            model.addAttribute("error", "Forkert brugernavn eller adgangskode");
            return "login";
        }
    }

    @GetMapping("/registrate")
    public String registrer(Model model) {
        User user = new User();
        //SHOuld this getmethod make the boolean isLoggedIn false per se?
        model.addAttribute("isLoggedIn", false);
        model.addAttribute("user", user);
        return "registrate";
    }

    @PostMapping("/new-user")
    public String createNewUser(@ModelAttribute User user, Model model) {
        try {
            User createdUser = service.createUserAndReturn(user);
            model.addAttribute("isLoggedIn", false);
            model.addAttribute("newUser", createdUser);
            return "registration-success";
        } catch (Exception e) {
            model.addAttribute("isLoggedIn", false);
            model.addAttribute("error", "Der opstod en fejl under oprettelsen af brugeren.");
            return "registrate";
        }
    }


    @GetMapping("/logout")
    public String logout() {
    }


    @GetMapping("/wishlists")
    public String wishlists(Model model) {
        return "redirect:/login";
    }
}
