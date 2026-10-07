package org.example.stepwave.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DashboardController {

    @GetMapping("/dashboard")
    public String showDashboard() {
        // Возвращаем имя HTML-шаблона без расширения
        return "dashboard";
    }
}