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
    private final UserService userService;

    public HomeController(UserService userService) {
        this.userService = userService;
    }

    // ===== Hjemmeside =====
    @GetMapping("/")
    public String index(HttpSession session, Model model) {
        model.addAttribute("isLoggedIn", session.getAttribute("userId") != null);
        return "index";
    }   //kig på det gamle undervisningsmateriale for session//
}
