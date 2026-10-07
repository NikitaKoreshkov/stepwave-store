package org.example.stepwave.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HelpController {

    // Страница "Связаться с нами"
    @GetMapping("/contact")
    public String contact(Model model) {
        model.addAttribute("pageTitle", "Связаться с нами");
        return "pages/contact"; // Указываем путь, включая папку pages
    }

    // Страница "Руководство"
    @GetMapping("/guide")
    public String guide(Model model) {
        model.addAttribute("pageTitle", "Руководство");
        return "pages/guide"; // Указываем путь, включая папку pages
    }

    // Страница "Акции и скидки"
    @GetMapping("/sales")
    public String sales(Model model) {
        model.addAttribute("pageTitle", "Акции и скидки");
        return "pages/sales"; // Указываем путь, включая папку pages
    }

    // Страница "Таблица размеров"
    @GetMapping("/size-guide")
    public String sizeGuide(Model model) {
        model.addAttribute("pageTitle", "Таблица размеров");
        return "pages/size-guide"; // Указываем путь, включая папку pages
    }

    // Страница "Рекомендации по продукции"
    @GetMapping("/recommendations")
    public String recommendations(Model model) {
        model.addAttribute("pageTitle", "Рекомендации по продукции");
        return "pages/recommendations"; // Указываем путь, включая папку pages
    }

    // Страница "Отзывы"
    @GetMapping("/reviews")
    public String reviews(Model model) {
        model.addAttribute("pageTitle", "Отзывы");
        return "pages/reviews"; // Указываем путь, включая папку pages
    }
}