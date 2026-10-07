package org.example.stepwave.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {

    // Главная страница (открывается по умолчанию)
    @GetMapping("/")
    public String showMainPage() {
        return "main"; // Страница main.html
    }

    // Страница регистрации
    @GetMapping("/signup")
    public String showSignupPage() {
        return "index"; // Страница для регистрации
    }
}